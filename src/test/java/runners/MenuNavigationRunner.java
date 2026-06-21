package runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

/**
 * TestNG runner for Menu Navigation tests.
 * Run this class to execute only MenuNavigation.feature scenarios.
 */
@CucumberOptions(
        features = "src/test/resources/features/MenuNavigation.feature",
        glue = {"stepdefinitions", "hooks"},
        tags = "@MenuNavigation",
        plugin = {
                "pretty",
                "html:target/cucumber-reports/menu-navigation/report.html",
                "json:target/cucumber-reports/menu-navigation/cucumber.json"
        }
)
public class MenuNavigationRunner extends AbstractTestNGCucumberTests {
}
