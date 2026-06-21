package pages;

import org.openqa.selenium.By;

/**
 * Page Object for the Register page (/register).
 * All locators for registration form elements are defined here.
 */
public class RegisterPage extends BasePage {

    // Locators - using id attributes from the register form
    public static final By REGISTER_TITLE = By.xpath("//h2[contains(.,'Create an account')]");
    public static final By FIRST_NAME_INPUT = By.id("firstName");
    public static final By LAST_NAME_INPUT = By.id("lastName");
    public static final By EMAIL_INPUT = By.id("email");
    public static final By PASSWORD_INPUT = By.id("password");
    public static final By CONFIRM_PASSWORD_INPUT = By.id("confirmPassword");
    public static final By REGISTER_SUBMIT = By.xpath("//button[contains(.,'Register')]");
    public static final By LOGIN_LINK = By.xpath("//a[contains(.,'Sign in')]");

    /** Navigates to the register page */
    public void navigateToRegisterPage() {
        openPath("/register");
        waitForVisible(REGISTER_TITLE);
    }

    /** Fills the registration form and submits */
    public void register(String firstName, String lastName, String email, String password) {
        type(FIRST_NAME_INPUT, firstName);
        type(LAST_NAME_INPUT, lastName);
        type(EMAIL_INPUT, email);
        type(PASSWORD_INPUT, password);
        type(CONFIRM_PASSWORD_INPUT, password);
        click(REGISTER_SUBMIT);
    }

    /** Waits for redirect to login page after successful registration */
    public void waitForRegistrationSuccess() {
        waitForUrlContains("/login");
    }

    /** Returns true if register page is displayed */
    public boolean isRegisterPageDisplayed() {
        return isDisplayed(REGISTER_TITLE);
    }

    /**
     * Registers the sample user if not already registered.
     * If email already exists, registration stays on register page - that is OK.
     */
    public void registerSampleUserIfNeeded(String email, String password) {
        navigateToRegisterPage();
        register("Auto", "Sample", email, password);

        try {
            waitForRegistrationSuccess();
        } catch (Exception e) {
            // User may already exist - continue to login
        }
    }
}
