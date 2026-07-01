package locators;

import org.openqa.selenium.By;

/**
 * Centralized object repository for all page locators.
 * Page classes reference these constants to keep locators in one place.
 */
public final class ObjectRepository {

    private ObjectRepository() {
    }

    public static final class Home {
        public static final By HERO_TITLE = By.cssSelector("[data-testid='hero-title']");
        public static final By HERO_SUBTITLE = By.cssSelector("[data-testid='hero-subtitle']");
        public static final By CURATED_COLLECTIONS = By.xpath("//h2[contains(.,'Curated Collections')]");
    }

    public static final class Login {
        public static final By LOGIN_TITLE = By.cssSelector("[data-testid='login-title']");
        public static final By EMAIL_INPUT = By.cssSelector("[data-testid='email-input']");
        public static final By PASSWORD_INPUT = By.cssSelector("[data-testid='password-input']");
        public static final By LOGIN_SUBMIT = By.cssSelector("[data-testid='login-submit'], button[type='submit']");
        public static final By ERROR_MESSAGE = By.cssSelector("[data-testid='error-message'], .text-red-500, [role='alert']");
        public static final By REGISTER_LINK = By.cssSelector("[data-testid='register-link']");
    }

    public static final class Navigation {
        public static final By PERFUMES_LINK = By.xpath("//header//a[normalize-space()='Perfumes']");
        public static final By COLLECTIONS_LINK = By.xpath("//header//a[normalize-space()='Collections']");
        public static final By OUR_STORY_LINK = By.xpath("//header//a[normalize-space()='Our Story']");
        public static final By LOGO_LINK = By.xpath("//header//a[contains(.,'ThePerfumeShop')]");
        public static final By SEARCH_BUTTON = By.cssSelector("button[aria-label='Search']");
        public static final By SEARCH_INPUT = By.cssSelector("input[type='search'], input[placeholder*='Search' i]");
    }

    public static final class Products {
        public static final By ADD_TO_CART_BTN = By.cssSelector("[data-testid='add-to-cart-btn']");
        public static final By PAGE_TITLE = By.xpath("//h1[contains(.,'Perfumes')]");
        public static final By PRODUCT_CARD = By.cssSelector("div.group.relative");
        public static final By PRODUCT_NAME = By.xpath("//h3");
        public static final By OUD_FILTER = By.xpath("//a[contains(@href,'category=oud')]//button");
        public static final By NATIVE_SELECT = By.tagName("select");
    }

    public static final class Checkout {
        public static final By FIRST_NAME = By.name("firstName");
        public static final By PAYMENT_COD = By.cssSelector("input[name='paymentMethod'][value='cod']");
        public static final By PLACE_ORDER_BTN = By.xpath("//button[contains(.,'Place Order')]");
    }

    public static final class Account {
        public static final By ACCOUNT_TITLE = By.xpath("//*[self::h1 or self::h2][contains(normalize-space(),'Account')]");
        public static final By ORDER_HISTORY_SECTION = By.xpath("//*[self::h2 or self::h3][contains(normalize-space(),'Order History')]");
    }
}
