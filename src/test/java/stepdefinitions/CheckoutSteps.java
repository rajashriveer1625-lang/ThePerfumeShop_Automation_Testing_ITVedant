package stepdefinitions;

import org.testng.Assert;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import pages.CheckoutPage;
import pages.CheckoutSuccessPage;

/**
 * Step definitions for Checkout feature.
 */
public class CheckoutSteps {

    private final CheckoutPage checkoutPage = new CheckoutPage();
    private final CheckoutSuccessPage checkoutSuccessPage = new CheckoutSuccessPage();

    @And("I fill shipping details with valid information")
    public void iFillShippingDetailsWithValidInformation() {
        checkoutPage.fillShippingDetails();
    }

    @And("I select Cash on Delivery payment method")
    public void iSelectCashOnDeliveryPaymentMethod() {
        checkoutPage.selectCashOnDelivery();
    }

    @And("I place the order")
    public void iPlaceTheOrder() {
        checkoutPage.clickPlaceOrder();
    }

    @Then("the order success page should be displayed")
    public void theOrderSuccessPageShouldBeDisplayed() {
        Assert.assertTrue(
                checkoutSuccessPage.isSuccessPageDisplayed(),
                "Order success page is not displayed"
        );
    }
}
