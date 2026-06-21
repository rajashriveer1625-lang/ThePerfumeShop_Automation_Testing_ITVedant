# How to Run Tests

## Prerequisites Checklist

- [ ] JDK 17 or higher installed (`java -version`)
- [ ] Maven 3.8+ installed (`mvn -version`)
- [ ] Google Chrome browser installed
- [ ] Sample user `abc@gmail.com` registered on the live site

## Method 1: Maven Command Line

Open a terminal in the project root folder.

### Run All Tests

```bash
mvn clean test -Dtest=MasterRunner
```

### Run Individual Test Areas

```bash
# 1. Home Page
mvn clean test -Dtest=HomePageRunner

# 2. Login
mvn clean test -Dtest=LoginRunner

# 3. Register
mvn clean test -Dtest=RegisterRunner

# 4. Menu Navigation
mvn clean test -Dtest=MenuNavigationRunner

# 5. Add to Cart
mvn clean test -Dtest=AddToCartRunner

# 6. Checkout
mvn clean test -Dtest=CheckoutRunner

# 7. My Account
mvn clean test -Dtest=MyAccountRunner
```

### Run Full TestNG Suite (All Runners in Order)

```bash
mvn clean test
```

This uses `testng.xml` which runs all 7 page runners plus the master runner.

## Method 2: IDE (Eclipse / IntelliJ)

### Eclipse — Option A: Run via TestNG Runner (Java 17)

Use this when you want to run one test area through its runner class (recommended for this project).

1. Import project: **File → Import → Existing Maven Projects**
2. Select the project folder
3. Wait for Maven to download dependencies
4. Navigate to `src/test/java/runners/`
5. Right-click the runner you need → **Run As → TestNG Test**

| Feature file | Runner class |
|--------------|--------------|
| `Login.feature` | `LoginRunner.java` |
| `Register.feature` | `RegisterRunner.java` |
| `HomePage.feature` | `HomePageRunner.java` |
| `MenuNavigation.feature` | `MenuNavigationRunner.java` |
| `AddToCart.feature` | `AddToCartRunner.java` |
| `Checkout.feature` | `CheckoutRunner.java` |
| `MyAccount.feature` | `MyAccountRunner.java` |
| All `@Smoke` scenarios | `MasterRunner.java` |

### Eclipse — Option B: Run Feature File Directly (needs Java 21+ for Eclipse)

Use this when you want to run a single `.feature` file from the editor.

**One-time setup (required):** The Cucumber Eclipse plugin needs **Java 21 or higher** to start. Your project can stay on Java 17; only Eclipse itself must use a newer JDK.

1. Install JDK 21+ if needed (JDK 25 on your machine also works)
2. **Window → Preferences → Java → Installed JREs**
3. Click **Add…** → **Standard VM** → point to your JDK 21+ folder (e.g. `C:\Program Files\Java\jdk-25`)
4. Check that JDK as the **default** JRE
5. Restart Eclipse
6. Install **Cucumber Eclipse Plugin** if not already installed: **Help → Eclipse Marketplace** → search **Cucumber**

**Run a feature file:**

1. Open a file under `src/test/resources/features/` (e.g. `Login.feature`)
2. Right-click in the editor → **Run As → Cucumber Feature**
3. On first run, if asked for glue, set: `stepdefinitions,hooks`  
   (This is also set in `src/test/resources/cucumber.properties`.)

**Option A vs Option B**

| | TestNG Runner | Feature file directly |
|--|---------------|------------------------|
| Java needed | Java 17 | Eclipse must use Java 21+ |
| Best for | Full framework run, reports, tags | Quick check of one feature |
| Browser hooks | Yes | Yes (via `cucumber.properties`) |

### IntelliJ IDEA

1. Open project: **File → Open** → select project folder
2. Wait for Maven import to finish
3. Navigate to `src/test/java/runners/`
4. Right-click any runner class
5. Select **Run 'HomePageRunner'**

### VS Code

1. Open project folder
2. Install "Extension Pack for Java" extension
3. Open a runner file
4. Click the **Run Test** link above the class name

## View Test Reports

After tests complete, HTML reports are generated at:

```
target/cucumber-reports/
├── homepage/index.html
├── login/index.html
├── register/index.html
├── menu-navigation/index.html
├── add-to-cart/index.html
├── checkout/index.html
├── my-account/index.html
└── master/index.html
```

Open any `index.html` file in Chrome to see:
- Pass/fail status for each scenario
- Step-by-step execution details
- Duration and error messages

## Failed Test Screenshots

When a test fails, a screenshot is automatically saved to:

```
target/screenshots/<scenario-name>_<timestamp>.png
```

## Troubleshooting

| Problem | Solution |
|---------|----------|
| `ChromeDriver` error | WebDriverManager auto-downloads it. Ensure internet access. |
| Login test fails | Register `abc@gmail.com` on the live site first. |
| Timeout errors | Increase `explicit.wait` in `config.properties`. |
| Cart empty in checkout | Ensure login step runs before add-to-cart in the same browser session. |
| Maven not found | Add Maven `bin` folder to your system PATH. |
| Java version error | Install JDK 17+ and set `JAVA_HOME`. |
| `UnsupportedClassVersionError` (61.0 vs 65.0) when running `.feature` | Eclipse is on Java 17; Cucumber plugin needs Java 21+. Set JDK 21+ as default JRE in Eclipse (see Option B above), or use **Run As → TestNG Test** on the runner instead. |

## Recommended First Run

Start with the simplest test to verify setup:

```bash
mvn clean test -Dtest=HomePageRunner
```

If that passes, try Register (creates its own user):

```bash
mvn clean test -Dtest=RegisterRunner
```

Then run the master suite:

```bash
mvn clean test -Dtest=MasterRunner
```
