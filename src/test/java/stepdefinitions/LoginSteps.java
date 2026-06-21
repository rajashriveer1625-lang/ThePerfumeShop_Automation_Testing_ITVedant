package stepdefinitions;

import io.cucumber.java.en.When;
import pages.LoginPage;

/**
 * Step definitions for Login feature.
 */
public class LoginSteps {

    private final LoginPage loginPage = new LoginPage();

    @When("I navigate to the login page")
    public void iNavigateToTheLoginPage() {
        loginPage.navigateToLoginPage();
    }
}
