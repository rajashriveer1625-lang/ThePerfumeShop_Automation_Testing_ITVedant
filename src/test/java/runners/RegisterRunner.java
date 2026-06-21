package runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

/**
 * TestNG runner for Register tests.
 * Run this class to execute only Register.feature scenarios.
 */
@CucumberOptions(
        features = "src/test/resources/features/Register.feature",
        glue = {"stepdefinitions", "hooks"},
        tags = "@Register",
        plugin = {
                "pretty",
                "html:target/cucumber-reports/register/report.html",
                "json:target/cucumber-reports/register/cucumber.json"
        }
)
public class RegisterRunner extends AbstractTestNGCucumberTests {
}
