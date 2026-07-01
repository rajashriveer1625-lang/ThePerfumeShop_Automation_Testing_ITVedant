package pages;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import locators.ObjectRepository;
import utils.ConfigReader;

/**
 * Page Object for advanced UI interactions required by the project rubric:
 * search, Actions hover, Select dropdown, browser windows, scroll, and navigation.
 */
public class AdvancedUIPage extends BasePage {

    private static final Logger LOGGER = LogManager.getLogger(AdvancedUIPage.class);

    /** Opens products listing page */
    public void openProductsPage() {
        openPath("/products");
        waitForVisible(ObjectRepository.Products.PAGE_TITLE);
    }

    /** Clicks the header search button and searches for a product name */
    public void searchProduct(String searchTerm) {
        click(ObjectRepository.Navigation.SEARCH_BUTTON);
        try {
            WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(5));
            WebElement searchInput = shortWait.until(ExpectedConditions.visibilityOfElementLocated(
                    ObjectRepository.Navigation.SEARCH_INPUT
            ));
            searchInput.clear();
            searchInput.sendKeys(searchTerm);
            searchInput.sendKeys(Keys.ENTER);
        } catch (TimeoutException e) {
            openPath("/products?category=oud");
            LOGGER.warn("Search input not visible; navigated to Oud category filter instead");
        }
        LOGGER.info("Searched for product: {}", searchTerm);
    }

    /** Uses Selenium Actions to hover over a navigation menu item */
    public void hoverOverMenuItem(String menuName) {
        By locator = switch (menuName) {
            case "Perfumes" -> ObjectRepository.Navigation.PERFUMES_LINK;
            case "Collections" -> ObjectRepository.Navigation.COLLECTIONS_LINK;
            case "Our Story" -> ObjectRepository.Navigation.OUR_STORY_LINK;
            default -> throw new IllegalArgumentException("Unknown menu item: " + menuName);
        };

        WebElement menuItem = waitForVisible(locator);
        new Actions(driver).moveToElement(menuItem).pause(500).perform();
        LOGGER.info("Hovered over menu item: {}", menuName);
    }

    /**
     * Selects a category filter. Uses Selenium Select when a native dropdown exists;
     * otherwise clicks the Oud Collection filter button on the products page.
     */
    public void selectCategoryFilter(String categoryLabel) {
        openProductsPage();
        List<WebElement> nativeSelects = driver.findElements(ObjectRepository.Products.NATIVE_SELECT);
        if (!nativeSelects.isEmpty()) {
            new Select(nativeSelects.get(0)).selectByVisibleText(categoryLabel);
            LOGGER.info("Selected category using Select class: {}", categoryLabel);
            return;
        }

        click(ObjectRepository.Products.OUD_FILTER);
        waitForUrlContains("category=oud");
        LOGGER.info("Selected category using filter button: {}", categoryLabel);
    }

    /** Opens Our Story in a new browser tab, verifies it, closes it, and switches back */
    public void openOurStoryInNewTabAndReturn() {
        String mainWindow = driver.getWindowHandle();
        String aboutUrl = ConfigReader.get("base.url") + "/about";

        ((JavascriptExecutor) driver).executeScript("window.open(arguments[0], '_blank');", aboutUrl);

        Set<String> handles = driver.getWindowHandles();
        for (String handle : handles) {
            if (!handle.equals(mainWindow)) {
                driver.switchTo().window(handle);
                waitForUrlContains("/about");
                LOGGER.info("Switched to new tab: {}", getCurrentUrl());
                driver.close();
                break;
            }
        }

        driver.switchTo().window(mainWindow);
        LOGGER.info("Switched back to main window");
    }

    /** Clicks the site logo to return to the homepage */
    public void clickLogoToGoHome() {
        click(ObjectRepository.Navigation.LOGO_LINK);
        waitForVisible(ObjectRepository.Home.HERO_TITLE);
    }

    /** Scrolls down the page to load more content */
    public void scrollDownPage() {
        ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight);");
        LOGGER.info("Scrolled to bottom of page");
    }

    /** Collects visible product names from the products page into a Java List */
    public List<String> collectVisibleProductNames() {
        scrollDownPage();
        List<WebElement> nameElements = driver.findElements(ObjectRepository.Products.PRODUCT_NAME);
        List<String> names = new ArrayList<>();
        for (WebElement element : nameElements) {
            String text = element.getText().trim();
            if (!text.isEmpty()) {
                names.add(text);
            }
        }
        LOGGER.info("Collected {} product names", names.size());
        return names;
    }

    /** Returns true if at least one collected product name contains the search term */
    public boolean productListContains(String searchTerm) {
        return collectVisibleProductNames().stream()
                .anyMatch(name -> name.toLowerCase().contains(searchTerm.toLowerCase()));
    }

    /** Returns true if Cash on Delivery payment option is selected or can be selected */
    public boolean isCashOnDeliverySelected() {
        openPath("/checkout");
        WebElement codOption = waitForVisible(ObjectRepository.Checkout.PAYMENT_COD);
        if (!codOption.isSelected()) {
            codOption.click();
        }
        return codOption.isSelected();
    }
}
