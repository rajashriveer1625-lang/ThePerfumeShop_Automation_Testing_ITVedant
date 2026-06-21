package stepdefinitions;

import org.testng.Assert;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pages.MyAccountPage;

/**
 * Step definitions for My Account feature.
 */
public class MyAccountSteps {

    private final MyAccountPage myAccountPage = new MyAccountPage();

    @When("I navigate to My Account page")
    public void iNavigateToMyAccountPage() {
        myAccountPage.openMyAccountPage();
    }

    @Then("the My Account page should be displayed")
    public void theMyAccountPageShouldBeDisplayed() {
        Assert.assertTrue(
                myAccountPage.isMyAccountPageDisplayed(),
                "My Account page is not displayed"
        );
    }

    @Then("the Order History section should be visible")
    public void theOrderHistorySectionShouldBeVisible() {
        Assert.assertTrue(
                myAccountPage.isOrderHistoryVisible(),
                "Order History section is not visible"
        );
    }
}
