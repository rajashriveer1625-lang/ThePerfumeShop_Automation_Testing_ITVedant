# The Perfume Shop - Selenium + Cucumber + TestNG Framework

A beginner-friendly automation testing framework for [The Perfume Shop demo site](https://perfume-demov-uksm.vercel.app/).

## Tech Stack

| Tool | Purpose |
|------|---------|
| **Java 17** | Programming language |
| **Selenium 4** | Browser automation |
| **Cucumber 7** | BDD (Behavior Driven Development) - write tests in plain English |
| **TestNG** | Test runner and reporting |
| **WebDriverManager** | Automatically downloads ChromeDriver |
| **Maven** | Build and dependency management |

## Quick Start

### Prerequisites

1. **JDK 17+** - [Download](https://adoptium.net/)
2. **Maven 3.8+** - [Download](https://maven.apache.org/download.cgi)
3. **Google Chrome** browser installed

### Setup

1. Clone or open this project in your IDE (Eclipse, IntelliJ, VS Code)
2. Register the sample user on the live site (one-time):
   - Go to https://perfume-demov-uksm.vercel.app/register
   - Email: `abc@gmail.com` | Password: `12345678`
3. Run Maven to download dependencies:

```bash
mvn clean compile test-compile
```

### Run Tests

```bash
# Run ALL tests (master runner)
mvn clean test -Dtest=MasterRunner

# Run a single test area
mvn clean test -Dtest=HomePageRunner
mvn clean test -Dtest=LoginRunner
mvn clean test -Dtest=RegisterRunner
mvn clean test -Dtest=MenuNavigationRunner
mvn clean test -Dtest=AddToCartRunner
mvn clean test -Dtest=CheckoutRunner
mvn clean test -Dtest=MyAccountRunner

# Run full TestNG suite (all runners in order)
mvn clean test
```

### View Reports

After running tests, open HTML reports in your browser:

```
target/cucumber-reports/<runner-name>/report.html
```

Example: `target/cucumber-reports/homepage/index.html`

Failed test screenshots are saved to: `target/screenshots/`

## Test Areas

| # | Feature | Runner Class | Description |
|---|---------|-------------|-------------|
| 1 | Home Page | `HomePageRunner` | Verify site opens and homepage is visible |
| 2 | Login | `LoginRunner` | Login with sample credentials |
| 3 | Register | `RegisterRunner` | Register new user and login |
| 4 | Menu Navigation | `MenuNavigationRunner` | Navigate main menu links |
| 5 | Add to Cart | `AddToCartRunner` | Login, add product, verify cart |
| 6 | Checkout | `CheckoutRunner` | Full checkout with Cash on Delivery |
| 7 | My Account | `MyAccountRunner` | View account page after login |
| - | **All Tests** | `MasterRunner` | Runs all 7 test areas |

## Configuration

Edit `src/test/resources/config.properties` to change:

- Site URL
- Sample user credentials
- Wait timeouts
- Shipping details for checkout

## Project Documentation

| Document | Description |
|----------|-------------|
| [docs/PROJECT_OVERVIEW.md](docs/PROJECT_OVERVIEW.md) | Framework purpose and site map |
| [docs/FRAMEWORK_STRUCTURE.md](docs/FRAMEWORK_STRUCTURE.md) | Folder layout and design patterns |
| [docs/HOW_TO_RUN.md](docs/HOW_TO_RUN.md) | Detailed run instructions |
| [docs/TEST_SCENARIOS.md](docs/TEST_SCENARIOS.md) | Step-by-step test walkthroughs |

## Target Application

- **Site URL:** https://perfume-demov-uksm.vercel.app/
- **Source Code:** https://github.com/kiranveer111/perfume_demov.git
- **Sample User:** abc@gmail.com / 12345678
