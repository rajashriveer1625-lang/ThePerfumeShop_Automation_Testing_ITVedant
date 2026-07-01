package pages;

import java.time.Duration;
import java.util.function.Supplier;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import utils.ConfigReader;
import utils.DriverFactory;

/**
 * Base class for all Page Object classes.
 * Contains shared WebDriver helpers used across every page.
 */
public abstract class BasePage {

    protected WebDriver driver;
    protected WebDriverWait wait;

    public BasePage() {
        this.driver = DriverFactory.getDriver();
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(ConfigReader.getInt("explicit.wait")));
    }

    /** Opens a full URL in the browser */
    public void open(String url) {
        driver.get(url);
    }

    /** Opens a path relative to base.url (e.g. "/login") */
    public void openPath(String path) {
        String baseUrl = ConfigReader.get("base.url");
        if (path.startsWith("/")) {
            open(baseUrl + path);
        } else {
            open(baseUrl + "/" + path);
        }
    }

    /** Returns the current page URL */
    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    /** Waits until an element is visible and returns it */
    protected WebElement waitForVisible(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    /** Waits until an element is clickable and clicks it */
    protected void click(By locator) {
        wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
    }

    /** Clears a field and types text into it */
    protected void type(By locator, String text) {
        WebElement element = waitForVisible(locator);
        typeInto(element, text);
    }

    /** Clears a field and types text without an extra visibility wait */
    protected void typeWithoutWait(By locator, String text) {
        typeInto(withZeroImplicitWait(() -> driver.findElement(locator)), text);
    }

    private void typeInto(WebElement element, String text) {
        element.clear();
        element.sendKeys(text);
    }

    /** Checks if an element is displayed on the page (waits up to explicit.wait) */
    protected boolean isDisplayed(By locator) {
        try {
            return waitForVisible(locator).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    /** Checks if an element is visible right now without implicit or explicit waiting */
    protected boolean isVisibleNow(By locator) {
        try {
            return withZeroImplicitWait(() -> driver.findElements(locator).stream()
                    .anyMatch(WebElement::isDisplayed));
        } catch (Exception e) {
            return false;
        }
    }

    protected <T> T withZeroImplicitWait(Supplier<T> action) {
        Duration originalWait = driver.manage().timeouts().getImplicitWaitTimeout();
        try {
            driver.manage().timeouts().implicitlyWait(Duration.ZERO);
            return action.get();
        } finally {
            driver.manage().timeouts().implicitlyWait(originalWait);
        }
    }

    /** Waits until URL contains the given text */
    protected void waitForUrlContains(String partialUrl) {
        wait.until(ExpectedConditions.urlContains(partialUrl));
    }

    /** Waits until the site cart is saved in browser localStorage */
    protected void waitForCartInLocalStorage() {
        wait.until(webDriver -> {
            Object hasItems = ((JavascriptExecutor) webDriver).executeScript(
                    "try { var c = localStorage.getItem('cart'); "
                            + "return c && JSON.parse(c).length > 0; } catch(e) { return false; }"
            );
            return Boolean.TRUE.equals(hasItems);
        });
    }

    /** Waits until a named product exists in localStorage cart */
    protected void waitForProductInCartStorage(String productName) {
        WebDriverWait cartWait = new WebDriverWait(driver, Duration.ofSeconds(20));
        cartWait.until(webDriver -> Boolean.TRUE.equals(((JavascriptExecutor) webDriver).executeScript(
                "var targetName = arguments[0]; "
                        + "try { "
                        + "var items = JSON.parse(localStorage.getItem('cart') || '[]'); "
                        + "return items.length > 0 && items.some(function(item) { "
                        + "  return item.name && item.name.indexOf(targetName) !== -1; "
                        + "}); "
                        + "} catch (e) { return false; }",
                productName
        )));
    }
}
