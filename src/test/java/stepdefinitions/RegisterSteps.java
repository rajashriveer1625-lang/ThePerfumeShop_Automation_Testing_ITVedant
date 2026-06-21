package stepdefinitions;

import java.util.Map;

import io.cucumber.java.en.And;
import io.cucumber.java.en.When;
import pages.RegisterPage;
import utils.TestContext;
import utils.TestDataGenerator;

/**
 * Step definitions for Register feature.
 */
public class RegisterSteps {

    private final RegisterPage registerPage = new RegisterPage();

    @When("I navigate to the register page")
    public void iNavigateToTheRegisterPage() {
        registerPage.navigateToRegisterPage();
    }

    @And("I register a new user with valid details")
    public void iRegisterANewUserWithValidDetails() {
        Map<String, String> user = TestDataGenerator.createNewUser();

        registerPage.register(
                user.get("firstName"),
                user.get("lastName"),
                user.get("email"),
                user.get("password")
        );

        TestContext.set("email", user.get("email"));
        TestContext.set("password", user.get("password"));

        registerPage.waitForRegistrationSuccess();
    }
}
