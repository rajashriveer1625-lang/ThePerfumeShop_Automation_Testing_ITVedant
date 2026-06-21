package runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

/**
 * TestNG runner for My Account tests.
 * Run this class to execute only MyAccount.feature scenarios.
 */
@CucumberOptions(
        features = "src/test/resources/features/MyAccount.feature",
        glue = {"stepdefinitions", "hooks"},
        tags = "@MyAccount",
        plugin = {
                "pretty",
                "html:target/cucumber-reports/my-account/report.html",
                "json:target/cucumber-reports/my-account/cucumber.json"
        }
)
public class MyAccountRunner extends AbstractTestNGCucumberTests {
}
