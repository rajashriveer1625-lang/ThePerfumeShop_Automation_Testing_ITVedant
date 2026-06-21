package pages;

import org.openqa.selenium.By;

import utils.ConfigReader;

/**
 * Page Object for the Home page (/).
 * All locators for the homepage are defined here.
 */
public class HomePage extends BasePage {

    // Locators
    public static final By HERO_TITLE = By.cssSelector("[data-testid='hero-title']");
    public static final By HERO_SUBTITLE = By.cssSelector("[data-testid='hero-subtitle']");
    public static final By CURATED_COLLECTIONS = By.xpath("//h2[contains(.,'Curated Collections')]");

    /** Opens the homepage */
    public void openHomePage() {
        open(ConfigReader.get("base.url"));
    }

    /** Returns true if hero title is visible */
    public boolean isHeroTitleVisible() {
        return isDisplayed(HERO_TITLE);
    }

    /** Returns true if hero subtitle is visible */
    public boolean isHeroSubtitleVisible() {
        return isDisplayed(HERO_SUBTITLE);
    }

    /** Returns the hero title text */
    public String getHeroTitleText() {
        return waitForVisible(HERO_TITLE).getText();
    }
}
