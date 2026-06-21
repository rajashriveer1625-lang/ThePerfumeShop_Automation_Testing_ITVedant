package stepdefinitions;

import org.testng.Assert;

import io.cucumber.java.en.Then;
import pages.HomePage;

/**
 * Step definitions for Home Page feature.
 */
public class HomePageSteps {

    private final HomePage homePage = new HomePage();

    @Then("the homepage hero title should be visible")
    public void theHomepageHeroTitleShouldBeVisible() {
        Assert.assertTrue(homePage.isHeroTitleVisible(), "Hero title is not visible on homepage");
    }

    @Then("the homepage subtitle should be visible")
    public void theHomepageSubtitleShouldBeVisible() {
        Assert.assertTrue(homePage.isHeroSubtitleVisible(), "Hero subtitle is not visible on homepage");
    }
}
