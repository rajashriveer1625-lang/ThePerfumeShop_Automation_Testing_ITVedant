package runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

/**
 * TestNG runner for Advanced UI interaction tests.
 */
@CucumberOptions(
        features = "src/test/resources/features/AdvancedUI.feature",
        glue = {"stepdefinitions", "hooks"},
        tags = "@AdvancedUI and @Smoke and not @Skip",
        plugin = {
                "pretty",
                "html:target/cucumber-reports/advanced-ui/report.html",
                "json:target/cucumber-reports/advanced-ui/cucumber.json"
        }
)
public class AdvancedUIRunner extends AbstractTestNGCucumberTests {
}
