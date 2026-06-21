# Framework Structure

## Folder Layout

```
The_Perfume_shop_Cucumber_Project/
├── pom.xml                          ← Maven build file (dependencies)
├── testng.xml                       ← TestNG suite (runs all runners)
├── README.md                        ← Quick start guide
├── docs/                            ← Detailed documentation
│
├── src/test/java/
│   ├── runners/                     ← TestNG runner classes (one per page)
│   │   ├── HomePageRunner.java
│   │   ├── LoginRunner.java
│   │   ├── RegisterRunner.java
│   │   ├── MenuNavigationRunner.java
│   │   ├── AddToCartRunner.java
│   │   ├── CheckoutRunner.java
│   │   ├── MyAccountRunner.java
│   │   └── MasterRunner.java      ← Runs ALL tests
│   │
│   ├── pages/                       ← Page Object Model (locators + actions)
│   │   ├── BasePage.java            ← Shared helpers (click, type, wait)
│   │   ├── HomePage.java
│   │   ├── LoginPage.java
│   │   ├── RegisterPage.java
│   │   ├── NavigationPage.java
│   │   ├── ProductsPage.java
│   │   ├── CartPage.java
│   │   ├── CheckoutPage.java
│   │   ├── CheckoutSuccessPage.java
│   │   └── MyAccountPage.java
│   │
│   ├── stepdefinitions/             ← Cucumber glue code
│   │   ├── CommonSteps.java         ← Shared steps (open site, login)
│   │   ├── HomePageSteps.java
│   │   ├── LoginSteps.java
│   │   ├── RegisterSteps.java
│   │   ├── NavigationSteps.java
│   │   ├── CartSteps.java
│   │   ├── CheckoutSteps.java
│   │   └── MyAccountSteps.java
│   │
│   ├── hooks/
│   │   └── Hooks.java               ← Browser setup/teardown, screenshots
│   │
│   └── utils/
│       ├── DriverFactory.java       ← Creates Chrome WebDriver
│       ├── ConfigReader.java        ← Reads config.properties
│       ├── TestDataGenerator.java   ← Generates unique register emails
│       └── TestContext.java         ← Shares data between steps
│
└── src/test/resources/
    ├── config.properties            ← URLs, credentials, timeouts
    └── features/                    ← Gherkin feature files
        ├── HomePage.feature
        ├── Login.feature
        ├── Register.feature
        ├── MenuNavigation.feature
        ├── AddToCart.feature
        ├── Checkout.feature
        └── MyAccount.feature
```

## How It All Connects

```
Runner (TestNG)
    ↓ reads
Feature File (.feature)          ← Plain English scenarios
    ↓ matched to
Step Definitions (.java)         ← Java code for each step
    ↓ calls
Page Objects (.java)             ← Locators + page actions
    ↓ uses
WebDriver (Chrome)               ← Controls the browser
    ↓ opens
Website (perfume-demov-uksm.vercel.app)
```

## Page Object Model (POM) Pattern

Each web page has a corresponding Java class. **All locators live in the page class**, never in step definitions.

Example from `LoginPage.java`:

```java
// Locators defined as constants
public static final By EMAIL_INPUT = By.cssSelector("[data-testid='email-input']");
public static final By PASSWORD_INPUT = By.cssSelector("[data-testid='password-input']");

// Actions as methods
public void login(String email, String password) {
    type(EMAIL_INPUT, email);
    type(PASSWORD_INPUT, password);
    click(LOGIN_SUBMIT);
}
```

Step definitions stay thin — they just call page methods:

```java
@When("I navigate to the login page")
public void iNavigateToTheLoginPage() {
    loginPage.navigateToLoginPage();
}
```

## Locator Strategy

Locators are prioritized in this order:

1. **`data-testid`** attributes (most stable, designed for testing)
2. **`id` / `name`** attributes (form fields)
3. **XPath with text** (navigation links, headings)

## Configuration

All environment-specific values are in `config.properties`:

```properties
base.url=https://perfume-demov-uksm.vercel.app
sample.email=abc@gmail.com
sample.password=12345678
implicit.wait=10
explicit.wait=15
```

Change these values instead of editing Java code.

## Tags

Each feature file has tags for selective execution:

| Tag | Feature File |
|-----|-------------|
| `@HomePage` | HomePage.feature |
| `@Login` | Login.feature |
| `@Register` | Register.feature |
| `@MenuNavigation` | MenuNavigation.feature |
| `@AddToCart` | AddToCart.feature |
| `@Checkout` | Checkout.feature |
| `@MyAccount` | MyAccount.feature |
| `@Smoke` | All features (used by MasterRunner) |
