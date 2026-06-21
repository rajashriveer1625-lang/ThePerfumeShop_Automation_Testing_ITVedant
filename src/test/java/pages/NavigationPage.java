package pages;

import org.openqa.selenium.By;

/**
 * Page Object for the Navigation bar (header menu).
 * Handles clicking menu links and verifying page URLs.
 */
public class NavigationPage extends BasePage {

    // Locators for desktop navigation links
    public static final By PERFUMES_LINK = By.xpath("//header//a[normalize-space()='Perfumes']");
    public static final By COLLECTIONS_LINK = By.xpath("//header//a[normalize-space()='Collections']");
    public static final By OUR_STORY_LINK = By.xpath("//header//a[normalize-space()='Our Story']");
    public static final By LOGO_LINK = By.xpath("//header//a[contains(.,'ThePerfumeShop')]");
    public static final By MY_ACCOUNT_LINK = By.xpath("//header//a[@href='/account']");

    /** Clicks a navigation menu item by its visible name */
    public void clickMenuItem(String menuName) {
        String path = switch (menuName) {
            case "Perfumes" -> "/products";
            case "Collections" -> "/categories";
            case "Our Story" -> "/about";
            default -> throw new IllegalArgumentException("Unknown menu item: " + menuName);
        };
        clickMenuItemByName(menuName);
        waitForUrlContains(path);
    }

    private void clickMenuItemByName(String menuName) {
        switch (menuName) {
            case "Perfumes" -> click(PERFUMES_LINK);
            case "Collections" -> click(COLLECTIONS_LINK);
            case "Our Story" -> click(OUR_STORY_LINK);
            default -> throw new IllegalArgumentException("Unknown menu item: " + menuName);
        }
    }

    /** Clicks the logo to go back to homepage */
    public void clickLogo() {
        click(LOGO_LINK);
    }

    /** Clicks the user/account icon in the header */
    public void clickMyAccountIcon() {
        click(MY_ACCOUNT_LINK);
    }

    /** Verifies current URL contains the expected path */
    public boolean isOnPage(String path) {
        waitForUrlContains(path);
        return getCurrentUrl().contains(path);
    }
}
