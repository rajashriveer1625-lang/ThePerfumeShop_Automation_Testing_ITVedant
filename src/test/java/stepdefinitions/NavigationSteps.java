package stepdefinitions;

import org.testng.Assert;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pages.NavigationPage;

/**
 * Step definitions for Menu Navigation feature.
 */
public class NavigationSteps {

    private final NavigationPage navigationPage = new NavigationPage();

    @When("I click on {string} in the navigation menu")
    public void iClickOnInTheNavigationMenu(String menuName) {
        navigationPage.clickMenuItem(menuName);
    }

    @Then("I should be on the products page")
    public void iShouldBeOnTheProductsPage() {
        Assert.assertTrue(
                navigationPage.isOnPage("/products"),
                "Expected products page but was: " + navigationPage.getCurrentUrl()
        );
    }

    @Then("I should be on the categories page")
    public void iShouldBeOnTheCategoriesPage() {
        Assert.assertTrue(
                navigationPage.isOnPage("/categories"),
                "Expected categories page but was: " + navigationPage.getCurrentUrl()
        );
    }

    @Then("I should be on the about page")
    public void iShouldBeOnTheAboutPage() {
        Assert.assertTrue(
                navigationPage.isOnPage("/about"),
                "Expected about page but was: " + navigationPage.getCurrentUrl()
        );
    }
}
