package runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

/**
 * TestNG runner for Checkout tests.
 * Run this class to execute only Checkout.feature scenarios.
 */
@CucumberOptions(
        features = "src/test/resources/features/Checkout.feature",
        glue = {"stepdefinitions", "hooks"},
        tags = "@Checkout",
        plugin = {
                "pretty",
                "html:target/cucumber-reports/checkout/report.html",
                "json:target/cucumber-reports/checkout/cucumber.json"
        }
)
public class CheckoutRunner extends AbstractTestNGCucumberTests {
}
