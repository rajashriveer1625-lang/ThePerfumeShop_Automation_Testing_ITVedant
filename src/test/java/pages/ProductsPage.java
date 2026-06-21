package pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import utils.ConfigReader;

/**
 * Page Object for Products pages (/products and /products/{id}).
 * Handles opening products and adding items to cart.
 */
public class ProductsPage extends BasePage {

    // Locators
    public static final By ADD_TO_CART_BTN = By.cssSelector("[data-testid='add-to-cart-btn']");

    /** Opens a specific product detail page by ID */
    public void openProductById(String productId) {
        openPath("/products/" + productId);
        waitForVisible(ADD_TO_CART_BTN);
        waitForProductPageReady();
    }

    /** Clicks the Add to Cart button once */
    public void clickAddToCart() {
        waitForProductPageReady();
        WebElement button = wait.until(ExpectedConditions.elementToBeClickable(ADD_TO_CART_BTN));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", button);

        try {
            button.click();
        } catch (Exception clickError) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", button);
        }
    }

    /** Adds a product to cart by name and verifies it was saved */
    public void addProductToCart(String productName) {
        String productId = ConfigReader.get("default.product.id");
        openProductById(productId);

        for (int attempt = 1; attempt <= 5; attempt++) {
            if (attempt > 1) {
                openProductById(productId);
            }

            clickAddToCart();

            if (waitForProductAddedToCart(productName)) {
                return;
            }
        }

        throw new RuntimeException(
                "Failed to add product to cart - '" + productName + "' was not saved"
        );
    }

    /** Waits for Next.js/React hydration so the cart button handler is active */
    private void waitForProductPageReady() {
        wait.until(webDriver -> "complete".equals(((JavascriptExecutor) webDriver)
                .executeScript("return document.readyState;")));

        WebDriverWait hydrationWait = new WebDriverWait(driver, Duration.ofSeconds(15));
        hydrationWait.until(webDriver -> Boolean.TRUE.equals(((JavascriptExecutor) webDriver)
                .executeScript(
                        "var btn = document.querySelector('[data-testid=\"add-to-cart-btn\"]');"
                                + "return btn && !btn.disabled && btn.offsetWidth > 0;"
                )));
    }

    private boolean waitForProductAddedToCart(String productName) {
        try {
            waitForProductInCartStorage(productName);
            return true;
        } catch (Exception storageError) {
            openPath("/cart");
            try {
                WebDriverWait cartWait = new WebDriverWait(driver, Duration.ofSeconds(15));
                cartWait.until(ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//h3[contains(.,'" + productName + "')]")
                ));
                return true;
            } catch (Exception uiError) {
                return false;
            }
        }
    }
}
