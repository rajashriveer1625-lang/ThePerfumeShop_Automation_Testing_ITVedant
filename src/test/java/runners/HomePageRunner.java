package runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

/**
 * TestNG runner for Home Page tests.
 * Run this class to execute only HomePage.feature scenarios.
 */
@CucumberOptions(
        features = "src/test/resources/features/HomePage.feature",
        glue = {"stepdefinitions", "hooks"},
        tags = "@HomePage",
        plugin = {
                "pretty",
                "html:target/cucumber-reports/homepage/report.html",
                "json:target/cucumber-reports/homepage/cucumber.json"
        }
)
public class HomePageRunner extends AbstractTestNGCucumberTests {
}
