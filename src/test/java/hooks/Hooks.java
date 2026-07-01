package hooks;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import utils.DriverFactory;
import utils.ExtentReportManager;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;

/**
 * Cucumber hooks that run before and after each scenario.
 * Sets up Chrome browser, logging, Extent reports, and failure screenshots.
 */
public class Hooks {

    private static final Logger LOGGER = LogManager.getLogger(Hooks.class);

    @Before
    public void setUp(Scenario scenario) {
        DriverFactory.initDriver();
        ExtentReportManager.startTest(scenario.getName());
        LOGGER.info("Starting scenario: {}", scenario.getName());
        ExtentReportManager.logInfo("Browser initialized");
    }

    @After
    public void tearDown(Scenario scenario) {
        if (scenario.isFailed()) {
            String screenshotPath = takeScreenshot(scenario.getName());
            ExtentReportManager.logFail("Scenario failed: " + scenario.getName());
            if (screenshotPath != null) {
                ExtentReportManager.logInfo("Screenshot saved: " + screenshotPath);
            }
            LOGGER.error("Scenario failed: {}", scenario.getName());
        } else {
            ExtentReportManager.logPass("Scenario passed: " + scenario.getName());
            LOGGER.info("Scenario passed: {}", scenario.getName());
        }

        ExtentReportManager.endTest(!scenario.isFailed());
        ExtentReportManager.flush();
        DriverFactory.quitDriver();
    }

    private String takeScreenshot(String scenarioName) {
        WebDriver driver = DriverFactory.getDriver();
        if (!(driver instanceof TakesScreenshot)) {
            return null;
        }

        File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        String safeName = scenarioName.replaceAll("[^a-zA-Z0-9]", "_");
        Path destination = Paths.get("target", "screenshots", safeName + "_" + timestamp + ".png");

        try {
            Files.createDirectories(destination.getParent());
            Files.copy(screenshot.toPath(), destination);
            LOGGER.info("Screenshot saved: {}", destination);
            return destination.toString();
        } catch (IOException e) {
            LOGGER.error("Failed to save screenshot: {}", e.getMessage());
            return null;
        }
    }
}
