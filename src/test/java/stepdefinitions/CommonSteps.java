package stepdefinitions;

import org.testng.Assert;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import pages.HomePage;
import pages.LoginPage;
import pages.RegisterPage;
import utils.ConfigReader;
import utils.TestContext;

/**
 * Common step definitions used across multiple features.
 * Handles opening the site and login actions.
 */
public class CommonSteps {

    private final HomePage homePage = new HomePage();
    private final LoginPage loginPage = new LoginPage();
    private final RegisterPage registerPage = new RegisterPage();

    @Given("I open The Perfume Shop website")
    public void iOpenThePerfumeShopWebsite() {
        TestContext.clear();
        homePage.openHomePage();
    }

    @And("I login with sample user credentials")
    public void iLoginWithSampleUserCredentials() {
        String email = ConfigReader.get("sample.email");
        String password = ConfigReader.get("sample.password");

        if (!tryLogin(email, password)) {
            registerPage.registerSampleUserIfNeeded(email, password);
            loginPage.navigateToLoginPage();
            Assert.assertTrue(tryLogin(email, password),
                    "Login failed even after attempting to register sample user: " + email);
        }
    }

    @And("I login with the newly registered user")
    public void iLoginWithTheNewlyRegisteredUser() {
        loginPage.navigateToLoginPage();
        Assert.assertTrue(
                tryLogin(TestContext.get("email"), TestContext.get("password")),
                "Login failed for newly registered user"
        );
    }

    @Then("I should be redirected to the products page")
    public void iShouldBeRedirectedToTheProductsPage() {
        Assert.assertTrue(
                loginPage.getCurrentUrl().contains("/products"),
                "Expected to be on products page but was: " + loginPage.getCurrentUrl()
        );
    }

    /** Attempts login and returns true if redirected to products page */
    private boolean tryLogin(String email, String password) {
        if (!loginPage.getCurrentUrl().contains("/login")) {
            loginPage.navigateToLoginPage();
        }
        loginPage.login(email, password);
        try {
            loginPage.waitForLoginSuccess();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
