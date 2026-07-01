package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;

/**
 * Page Object for the My Account page (/account).
 * Verifies account profile and order history sections.
 */
public class MyAccountPage extends BasePage {

    // Locators
    public static final By ACCOUNT_TITLE = By.xpath("//*[self::h1 or self::h2][contains(normalize-space(),'Account')]");
    public static final By ORDER_HISTORY_SECTION = By.xpath("//*[self::h2 or self::h3][contains(normalize-space(),'Order History')]");
    public static final By MY_ACCOUNT_LINK = By.xpath("//header//a[@href='/account']");
    public static final By USER_EMAIL = By.xpath("//p[contains(@class,'text-muted') or contains(@class,'text-gray')]");

    /** Opens My Account via header link to preserve client-side auth state */
    public void openMyAccountPage() {
        try {
            click(MY_ACCOUNT_LINK);
        } catch (Exception e) {
            openPath("/account");
        }
        waitForAccountPageReady();
    }

    /** Waits until account URL and key content are available */
    public void waitForAccountPageReady() {
        waitForUrlContains("/account");
        wait.until(ExpectedConditions.or(
                ExpectedConditions.visibilityOfElementLocated(ACCOUNT_TITLE),
                ExpectedConditions.visibilityOfElementLocated(ORDER_HISTORY_SECTION)
        ));
    }

    /** Returns true if My Account page title is visible */
    public boolean isMyAccountPageDisplayed() {
        waitForAccountPageReady();
        return isDisplayed(ACCOUNT_TITLE);
    }

    /** Returns true if Order History section is visible */
    public boolean isOrderHistoryVisible() {
        return isDisplayed(ORDER_HISTORY_SECTION);
    }
}
