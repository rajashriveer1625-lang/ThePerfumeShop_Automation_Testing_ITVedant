package utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

/**
 * Creates and manages the Extent HTML report for Cucumber scenarios.
 */
public final class ExtentReportManager {

    private static ExtentReports extentReports;
    private static final ThreadLocal<ExtentTest> extentTest = new ThreadLocal<>();

    private ExtentReportManager() {
    }

    public static synchronized ExtentReports getReporter() {
        if (extentReports == null) {
            ExtentSparkReporter spark = new ExtentSparkReporter("target/extent-reports/ExtentReport.html");
            spark.config().setTheme(Theme.STANDARD);
            spark.config().setDocumentTitle("The Perfume Shop - Automation Report");
            spark.config().setReportName("Cucumber Test Execution");

            extentReports = new ExtentReports();
            extentReports.attachReporter(spark);
            extentReports.setSystemInfo("Project", "The Perfume Shop Cucumber Framework");
            extentReports.setSystemInfo("Browser", ConfigReader.get("browser"));
        }
        return extentReports;
    }

    public static void startTest(String scenarioName) {
        ExtentTest test = getReporter().createTest(scenarioName);
        extentTest.set(test);
    }

    public static ExtentTest getTest() {
        return extentTest.get();
    }

    public static void logInfo(String message) {
        ExtentTest test = extentTest.get();
        if (test != null) {
            test.info(message);
        }
    }

    public static void logPass(String message) {
        ExtentTest test = extentTest.get();
        if (test != null) {
            test.pass(message);
        }
    }

    public static void logFail(String message) {
        ExtentTest test = extentTest.get();
        if (test != null) {
            test.fail(message);
        }
    }

    public static void endTest(boolean passed) {
        ExtentTest test = extentTest.get();
        if (test != null) {
            if (passed) {
                test.pass("Scenario passed");
            } else {
                test.fail("Scenario failed");
            }
        }
        extentTest.remove();
    }

    public static void flush() {
        if (extentReports != null) {
            extentReports.flush();
        }
    }
}
