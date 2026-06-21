package pages;

import org.openqa.selenium.By;

/**
 * Page Object for the My Account page (/account).
 * Verifies account profile and order history sections.
 */
public class MyAccountPage extends BasePage {

    // Locators
    public static final By ACCOUNT_TITLE = By.xpath("//h1[contains(.,'My Account')]");
    public static final By ORDER_HISTORY_SECTION = By.xpath("//h2[contains(.,'Order History')]");
    public static final By USER_EMAIL = By.xpath("//p[contains(@class,'text-muted') or contains(@class,'text-gray')]");

    /** Opens the My Account page directly */
    public void openMyAccountPage() {
        openPath("/account");
    }

    /** Returns true if My Account page title is visible */
    public boolean isMyAccountPageDisplayed() {
        waitForUrlContains("/account");
        return isDisplayed(ACCOUNT_TITLE);
    }

    /** Returns true if Order History section is visible */
    public boolean isOrderHistoryVisible() {
        return isDisplayed(ORDER_HISTORY_SECTION);
    }
}
