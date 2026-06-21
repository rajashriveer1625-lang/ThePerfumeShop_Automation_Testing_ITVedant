package stepdefinitions;

import org.testng.Assert;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pages.CartPage;
import pages.CheckoutPage;
import pages.LoginPage;
import pages.ProductsPage;
import utils.ConfigReader;

/**
 * Step definitions for Add to Cart feature.
 */
public class CartSteps {

    private final ProductsPage productsPage = new ProductsPage();
    private final CartPage cartPage = new CartPage();
    private final CheckoutPage checkoutPage = new CheckoutPage();
    private final LoginPage loginPage = new LoginPage();

    @When("I add {string} product to the cart")
    public void iAddProductToTheCart(String productName) {
        productsPage.addProductToCart(productName);
    }

    @And("I open the cart page")
    public void iOpenTheCartPage() {
        cartPage.openCartPage();

        String productName = ConfigReader.get("default.product.name");
        if (!cartPage.isProductInCart(productName)) {
            productsPage.addProductToCart(productName);
            cartPage.openCartPage();
        }

        Assert.assertTrue(
                cartPage.isProductInCart(productName),
                "Cart is empty - product '" + productName + "' was not added"
        );
    }

    @Then("the cart should contain {string}")
    public void theCartShouldContain(String productName) {
        Assert.assertTrue(
                cartPage.isProductInCart(productName),
                "Product '" + productName + "' was not found in the cart"
        );
    }

    @When("I proceed to checkout")
    public void iProceedToCheckout() {
        String productName = ConfigReader.get("default.product.name");
        Assert.assertTrue(
                cartPage.isProductInCart(productName),
                "Cannot checkout - cart does not contain '" + productName + "'"
        );

        cartPage.clickCheckout();

        if (loginPage.getCurrentUrl().contains("/login")) {
            loginPage.login(
                    ConfigReader.get("sample.email"),
                    ConfigReader.get("sample.password")
            );
            loginPage.waitForLoginSuccess();
            cartPage.openCartPage();
            cartPage.clickCheckout();
        }

        checkoutPage.waitForCheckoutPage();
    }

    @Then("the checkout page should be displayed")
    public void theCheckoutPageShouldBeDisplayed() {
        Assert.assertTrue(
                checkoutPage.isCheckoutPageDisplayed(),
                "Checkout page is not displayed"
        );
    }
}
