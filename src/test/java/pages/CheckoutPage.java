package pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import utils.ConfigReader;

/**
 * Page Object for the Checkout page (/checkout).
 * Handles shipping form, payment method selection, and placing orders.
 */
public class CheckoutPage extends BasePage {

    // Locators - using name attributes from the checkout form
    public static final By FIRST_NAME = By.name("firstName");
    public static final By LAST_NAME = By.name("lastName");
    public static final By ADDRESS = By.name("address");
    public static final By CITY = By.name("city");
    public static final By ZIP_CODE = By.name("zipCode");
    public static final By PAYMENT_COD = By.cssSelector("input[name='paymentMethod'][value='cod']");
    public static final By PLACE_ORDER_BTN = By.xpath("//button[contains(.,'Place Order')]");
    public static final By EMPTY_CART_MESSAGE = By.xpath("//*[contains(.,'Your Cart is Empty')]");

    /** Returns true if checkout page with shipping form is visible */
    public boolean isCheckoutPageDisplayed() {
        return getCurrentUrl().contains("/checkout") && isVisibleNow(FIRST_NAME);
    }

    /** Fills shipping details using values from config.properties */
    public void fillShippingDetails() {
        waitForShippingFormReady();
        typeWithoutWait(FIRST_NAME, ConfigReader.get("shipping.firstName"));
        typeWithoutWait(LAST_NAME, ConfigReader.get("shipping.lastName"));
        typeWithoutWait(ADDRESS, ConfigReader.get("shipping.address"));
        typeWithoutWait(CITY, ConfigReader.get("shipping.city"));
        typeWithoutWait(ZIP_CODE, ConfigReader.get("shipping.zipCode"));
    }

    private void waitForShippingFormReady() {
        wait.until(ExpectedConditions.and(
                ExpectedConditions.visibilityOfElementLocated(FIRST_NAME),
                ExpectedConditions.visibilityOfElementLocated(LAST_NAME),
                ExpectedConditions.visibilityOfElementLocated(ADDRESS),
                ExpectedConditions.visibilityOfElementLocated(CITY),
                ExpectedConditions.visibilityOfElementLocated(ZIP_CODE)
        ));
    }

    /** Selects Cash on Delivery payment method */
    public void selectCashOnDelivery() {
        click(PAYMENT_COD);
    }

    /** Clicks Place Order and waits for success page or error alert */
    public void clickPlaceOrder() {
        click(PLACE_ORDER_BTN);

        WebDriverWait orderWait = new WebDriverWait(driver, Duration.ofSeconds(30));
        try {
            orderWait.until(ExpectedConditions.or(
                    ExpectedConditions.urlContains("/checkout/success"),
                    ExpectedConditions.alertIsPresent()
            ));
        } catch (TimeoutException e) {
            throw new RuntimeException(
                    "Order was not placed. Current URL: " + getCurrentUrl()
                            + ". Ensure the cart has items before checkout."
            );
        }

        handleCheckoutAlertIfPresent();
    }

    private void handleCheckoutAlertIfPresent() {
        try {
            String alertText = driver.switchTo().alert().getText();
            driver.switchTo().alert().accept();
            throw new RuntimeException("Checkout failed: " + alertText);
        } catch (NoAlertPresentException ignored) {
            // Success path - no alert shown
        }
    }

    /** Opens the checkout page directly */
    public void openCheckoutPage() {
        openPath("/checkout");
    }

    /** Waits for checkout page to load with shipping form visible */
    public void waitForCheckoutPage() {
        WebDriverWait extendedWait = new WebDriverWait(
                driver, Duration.ofSeconds(ConfigReader.getInt("explicit.wait") + 15)
        );
        extendedWait.until(ExpectedConditions.and(
                ExpectedConditions.urlContains("/checkout"),
                ExpectedConditions.visibilityOfElementLocated(FIRST_NAME)
        ));

        if (isVisibleNow(EMPTY_CART_MESSAGE)) {
            throw new RuntimeException("Checkout blocked - cart is empty on checkout page");
        }
    }
}
