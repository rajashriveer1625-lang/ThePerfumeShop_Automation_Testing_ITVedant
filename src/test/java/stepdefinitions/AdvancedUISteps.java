package stepdefinitions;

import java.util.List;
import java.util.Map;

import org.testng.Assert;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pages.AdvancedUIPage;
import pages.CartPage;
import pages.LoginPage;
import pages.ProductsPage;
import utils.ConfigReader;
import utils.ExcelReader;
import utils.ExtentReportManager;

/**
 * Step definitions for AdvancedUI.feature — covers search, Actions, Select,
 * browser windows, scrolling, Collections, and Excel data-driven login.
 */
public class AdvancedUISteps {

    private final AdvancedUIPage advancedUIPage = new AdvancedUIPage();
    private final LoginPage loginPage = new LoginPage();
    private final ProductsPage productsPage = new ProductsPage();
    private final CartPage cartPage = new CartPage();

    @When("I hover over {string} in the navigation menu using Actions")
    public void iHoverOverInTheNavigationMenuUsingActions(String menuName) {
        advancedUIPage.hoverOverMenuItem(menuName);
        ExtentReportManager.logInfo("Hovered over menu: " + menuName);
    }

    @And("I open the products page")
    public void iOpenTheProductsPage() {
        advancedUIPage.openProductsPage();
    }

    @And("I search for {string} in the product search")
    public void iSearchForInTheProductSearch(String searchTerm) {
        advancedUIPage.searchProduct(searchTerm);
    }

    @And("I filter products by {string} category")
    public void iFilterProductsByCategory(String categoryLabel) {
        advancedUIPage.selectCategoryFilter(categoryLabel);
    }

    @Then("the product results should contain {string}")
    public void theProductResultsShouldContain(String expectedText) {
        List<String> productNames = advancedUIPage.collectVisibleProductNames();
        Assert.assertFalse(productNames.isEmpty(), "Expected product names on page");
        Assert.assertTrue(
                advancedUIPage.productListContains(expectedText),
                "Expected product list to contain '" + expectedText + "' but found: " + productNames
        );
        ExtentReportManager.logPass("Product results contain: " + expectedText);
    }

    @When("I open {string} in a new browser tab and switch back")
    public void iOpenInANewBrowserTabAndSwitchBack(String pageName) {
        if ("Our Story".equals(pageName)) {
            advancedUIPage.openOurStoryInNewTabAndReturn();
            ExtentReportManager.logInfo("Verified new browser tab handling for: " + pageName);
            return;
        }
        throw new IllegalArgumentException("Unsupported page for new tab: " + pageName);
    }

    @And("I click the logo to return to the homepage")
    public void iClickTheLogoToReturnToTheHomepage() {
        advancedUIPage.clickLogoToGoHome();
    }

    @And("I attempt login with invalid credentials from Excel")
    public void iAttemptLoginWithInvalidCredentialsFromExcel() {
        Map<String, String> row = ExcelReader.readFirstRow("testdata/LoginData.xlsx", "InvalidLogin");
        String email = row.getOrDefault("Email", ConfigReader.get("sample.email"));
        String password = row.getOrDefault("Password", "wrong-password");
        loginPage.login(email, password);
        ExtentReportManager.logInfo("Attempted invalid login using Excel data for: " + email);
    }

    @Then("I should see a login warning or remain on login page")
    public void iShouldSeeALoginWarningOrRemainOnLoginPage() {
        Assert.assertTrue(
                loginPage.isErrorMessageDisplayed(),
                "Expected login warning or to remain on login page after invalid credentials"
        );
        ExtentReportManager.logPass("Invalid login handled: " + loginPage.getErrorMessageText());
    }

    @And("I add the default product to cart")
    public void iAddTheDefaultProductToCart() {
        productsPage.addProductToCart(ConfigReader.get("default.product.name"));
        cartPage.openCartPage();
    }

    @When("I verify Cash on Delivery payment option is selectable")
    public void iVerifyCashOnDeliveryPaymentOptionIsSelectable() {
        advancedUIPage.isCashOnDeliverySelected();
    }

    @Then("the Cash on Delivery option should be selected")
    public void theCashOnDeliveryOptionShouldBeSelected() {
        Assert.assertTrue(advancedUIPage.isCashOnDeliverySelected(),
                "Cash on Delivery payment option should be selected");
    }
}
