package runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

/**
 * TestNG runner for Login tests.
 * Run this class to execute only Login.feature scenarios.
 */
@CucumberOptions(
        features = "src/test/resources/features/Login.feature",
        glue = {"stepdefinitions", "hooks"},
        tags = "@Login",
        plugin = {
                "pretty",
                "html:target/cucumber-reports/login/report.html",
                "json:target/cucumber-reports/login/cucumber.json"
        }
)
public class LoginRunner extends AbstractTestNGCucumberTests {
}
