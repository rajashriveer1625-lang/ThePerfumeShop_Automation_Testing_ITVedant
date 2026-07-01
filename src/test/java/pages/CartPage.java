package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;

/**
 * Page Object for the Cart page (/cart).
 * Handles cart verification and proceeding to checkout.
 */
public class CartPage extends BasePage {

    // Locators
    public static final By CART_EMPTY_MESSAGE = By.xpath("//*[contains(.,'Your Cart is Empty')]");
    public static final By CART_TITLE = By.xpath("//h1[contains(.,'Shopping Cart')]");
    public static final By CHECKOUT_BUTTON = By.xpath("//a[contains(@href,'/checkout')]");
    public static final By CHECKOUT_LOGIN_LINK = By.xpath("//a[contains(@href,'/login') and contains(@href,'cart')]");
    public static final By START_SHOPPING_LINK = By.xpath("//a[contains(.,'Start Shopping')]");

    /** Opens the cart page directly and waits for it to load */
    public void openCartPage() {
        openPath("/cart");
        waitForCartPageToLoad();
    }

    /** Waits until cart page shows items or empty state */
    private void waitForCartPageToLoad() {
        wait.until(ExpectedConditions.or(
                ExpectedConditions.visibilityOfElementLocated(CART_TITLE),
                ExpectedConditions.visibilityOfElementLocated(CART_EMPTY_MESSAGE)
        ));
    }

    /** Checks if a product name appears in the cart */
    public boolean isProductInCart(String productName) {
        By productLocator = By.xpath("//h3[contains(.,'" + productName + "')]");

        try {
            wait.until(webDriver -> {
                if (!getCurrentUrl().contains("/cart")) {
                    openPath("/cart");
                }
                return !webDriver.findElements(productLocator).isEmpty()
                        && webDriver.findElement(productLocator).isDisplayed();
            });
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /** Clicks the Checkout link to proceed to checkout */
    public void clickCheckout() {
        By checkoutControl = wait.until(webDriver -> {
            if (isVisibleNow(CHECKOUT_BUTTON)) {
                return CHECKOUT_BUTTON;
            }
            if (isVisibleNow(CHECKOUT_LOGIN_LINK)) {
                return CHECKOUT_LOGIN_LINK;
            }
            return null;
        });
        click(checkoutControl);

        wait.until(ExpectedConditions.or(
                ExpectedConditions.urlContains("/checkout"),
                ExpectedConditions.urlContains("/login")
        ));
    }

    /** Returns true if cart page is loaded */
    public boolean isCartPageDisplayed() {
        waitForUrlContains("/cart");
        return getCurrentUrl().contains("/cart");
    }
}
