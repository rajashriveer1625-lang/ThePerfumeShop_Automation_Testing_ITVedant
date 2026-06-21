package pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import utils.ConfigReader;

/**
 * Page Object for the Checkout Success page (/checkout/success).
 * Verifies order was placed successfully.
 */
public class CheckoutSuccessPage extends BasePage {

    // Locators
    public static final By SUCCESS_TITLE = By.xpath("//*[contains(.,'Order Placed Successfully!')]");
    public static final By SUCCESS_MESSAGE = By.xpath("//*[contains(.,'Thank you for your purchase')]");
    public static final By VIEW_ORDERS_LINK = By.xpath("//a[contains(.,'View your orders')]");
    public static final By CONTINUE_SHOPPING_LINK = By.xpath("//a[contains(.,'Continue Shopping')]");

    /** Returns true if order success page is displayed */
    public boolean isSuccessPageDisplayed() {
        WebDriverWait extendedWait = new WebDriverWait(
                driver, Duration.ofSeconds(ConfigReader.getInt("explicit.wait") + 15)
        );
        extendedWait.until(ExpectedConditions.or(
                ExpectedConditions.urlContains("/checkout/success"),
                ExpectedConditions.visibilityOfElementLocated(SUCCESS_TITLE)
        ));
        return getCurrentUrl().contains("/checkout/success");
    }

    /** Returns the success title text */
    public String getSuccessTitleText() {
        return waitForVisible(SUCCESS_TITLE).getText();
    }
}
