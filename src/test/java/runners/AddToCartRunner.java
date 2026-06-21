package runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

/**
 * TestNG runner for Add to Cart tests.
 * Run this class to execute only AddToCart.feature scenarios.
 */
@CucumberOptions(
        features = "src/test/resources/features/AddToCart.feature",
        glue = {"stepdefinitions", "hooks"},
        tags = "@AddToCart",
        plugin = {
                "pretty",
                "html:target/cucumber-reports/add-to-cart/report.html",
                "json:target/cucumber-reports/add-to-cart/cucumber.json"
        }
)
public class AddToCartRunner extends AbstractTestNGCucumberTests {
}
