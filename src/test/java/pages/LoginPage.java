package pages;

import locators.ObjectRepository;

/**
 * Page Object for the Login page (/login).
 * All locators for login form elements are defined here.
 */
public class LoginPage extends BasePage {

    /** Navigates to the login page */
    public void navigateToLoginPage() {
        openPath("/login");
        waitForVisible(ObjectRepository.Login.LOGIN_TITLE);
    }

    /** Enters email and password, then clicks Sign in */
    public void login(String email, String password) {
        type(ObjectRepository.Login.EMAIL_INPUT, email);
        type(ObjectRepository.Login.PASSWORD_INPUT, password);
        click(ObjectRepository.Login.LOGIN_SUBMIT);
    }

    /** Returns true if login page title is visible */
    public boolean isLoginPageDisplayed() {
        return isDisplayed(ObjectRepository.Login.LOGIN_TITLE);
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

    /** Returns true if an error or warning message is visible after failed login */
    public boolean isErrorMessageDisplayed() {
        try {
            wait.until(webDriver -> {
                if (isDisplayed(ObjectRepository.Login.ERROR_MESSAGE)) {
                    return true;
                }
                return !getCurrentUrl().contains("/products");
            });
            return isDisplayed(ObjectRepository.Login.ERROR_MESSAGE)
                    || getCurrentUrl().contains("/login");
        } catch (Exception e) {
            return getCurrentUrl().contains("/login");
        }
    }

    /** Returns visible error/warning text if present */
    public String getErrorMessageText() {
        if (isDisplayed(ObjectRepository.Login.ERROR_MESSAGE)) {
            return waitForVisible(ObjectRepository.Login.ERROR_MESSAGE).getText();
        }
        return "Login failed - still on login page";
    }
}
