package hooks;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import utils.DriverFactory;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;

/**
 * Cucumber hooks that run before and after each scenario.
 * Sets up Chrome browser before test and closes it after test.
 * Takes a screenshot when a scenario fails.
 */
public class Hooks {

    @Before
    public void setUp() {
        DriverFactory.initDriver();
    }

    @After
    public void tearDown(Scenario scenario) {
        if (scenario.isFailed()) {
            takeScreenshot(scenario.getName());
        }
        DriverFactory.quitDriver();
    }

    private void takeScreenshot(String scenarioName) {
        WebDriver driver = DriverFactory.getDriver();
        if (driver instanceof TakesScreenshot) {
            File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
            String safeName = scenarioName.replaceAll("[^a-zA-Z0-9]", "_");
            Path destination = Paths.get("target", "screenshots", safeName + "_" + timestamp + ".png");

            try {
                Files.createDirectories(destination.getParent());
                Files.copy(screenshot.toPath(), destination);
                System.out.println("Screenshot saved: " + destination);
            } catch (IOException e) {
                System.err.println("Failed to save screenshot: " + e.getMessage());
            }
        }
    }
}
