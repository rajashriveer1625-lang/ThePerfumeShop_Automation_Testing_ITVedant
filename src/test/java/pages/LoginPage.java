package pages;

import org.openqa.selenium.By;

/**
 * Page Object for the Login page (/login).
 * All locators for login form elements are defined here.
 */
public class LoginPage extends BasePage {

    // Locators
    public static final By LOGIN_TITLE = By.cssSelector("[data-testid='login-title']");
    public static final By EMAIL_INPUT = By.cssSelector("[data-testid='email-input']");
    public static final By PASSWORD_INPUT = By.cssSelector("[data-testid='password-input']");
    public static final By LOGIN_SUBMIT = By.cssSelector(
            "[data-testid='login-submit'], button[type='submit']"
    );
    public static final By ERROR_MESSAGE = By.cssSelector("[data-testid='error-message']");
    public static final By REGISTER_LINK = By.cssSelector("[data-testid='register-link']");

    /** Navigates to the login page */
    public void navigateToLoginPage() {
        openPath("/login");
        waitForVisible(LOGIN_TITLE);
    }

    /** Enters email and password, then clicks Sign in */
    public void login(String email, String password) {
        type(EMAIL_INPUT, email);
        type(PASSWORD_INPUT, password);
        click(LOGIN_SUBMIT);
    }

    /** Returns true if login page title is visible */
    public boolean isLoginPageDisplayed() {
        return isDisplayed(LOGIN_TITLE);
    }

    /** Waits for redirect to products page after successful login */
    public void waitForLoginSuccess() {
        waitForUrlContains("/products");
    }

    /** Performs full login flow from any page */
    public void loginFromHome(String email, String password) {
        navigateToLoginPage();
        login(email, password);
        waitForLoginSuccess();
    }
}
