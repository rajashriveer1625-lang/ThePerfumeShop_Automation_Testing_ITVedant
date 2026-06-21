# Project Overview

## What Is This Project?

This is an **automation testing framework** for The Perfume Shop e-commerce demo website. It uses Selenium WebDriver to control a Chrome browser and automatically verify that the website works correctly.

## Why Cucumber + TestNG?

- **Cucumber** lets you write test scenarios in plain English (Gherkin language). Anyone can read and understand what is being tested.
- **TestNG** runs the tests and generates reports. Each page has its own runner so you can test one area at a time.
- **Page Object Model (POM)** keeps all web element locators in separate page classes, making tests easy to maintain.

## Target Website


| Item        | Value                                                                                                  |
| ----------- | ------------------------------------------------------------------------------------------------------ |
| Live Site   | [https://perfume-demov-uksm.vercel.app/](https://perfume-demov-uksm.vercel.app/)                       |
| GitHub Repo | [https://github.com/kiranveer111/perfume_demov.git](https://github.com/kiranveer111/perfume_demov.git) |
| Technology  | Next.js, React, NextAuth                                                                               |


## Site Pages Map

```
/                    → Home Page
/login               → Login Page
/register            → Register Page
/products            → Products Listing
/products/{id}       → Product Detail (Add to Cart)
/categories          → Collections Page
/about               → Our Story Page
/cart                → Shopping Cart
/checkout            → Checkout (requires login)
/checkout/success    → Order Confirmation
/account             → My Account / Order History (requires login)
```

## Test Sequence

Tests are designed to run in this order:

```
1. HomePage        → Site opens, hero visible
2. Login           → Login with sample user
3. Register        → Create new user, then login
4. MenuNavigation  → Click Perfumes, Collections, Our Story
5. AddToCart       → Login → Add product → Verify cart → Checkout page
6. Checkout        → Login → Add product → Fill form → COD → Place order
7. MyAccount       → Login → View account page
```

## Sample User Credentials


| Field    | Value                                 |
| -------- | ------------------------------------- |
| Email    | [abc@gmail.com](mailto:abc@gmail.com) |
| Password | 12345678                              |


**Important:** The demo site does not come with pre-seeded users. You must register `abc@gmail.com` once on the live site before running Login, AddToCart, Checkout, or MyAccount tests. Alternatively, run the Register test first — it creates its own unique user automatically.

## Glossary


| Term                | Meaning                                                                    |
| ------------------- | -------------------------------------------------------------------------- |
| **WebDriver**       | Selenium component that controls the browser                               |
| **Locator**         | A way to find an element on a web page (CSS selector, XPath, id)           |
| **Page Object**     | A Java class that represents one web page with its locators and actions    |
| **Feature File**    | A `.feature` file with test scenarios written in Gherkin (Given/When/Then) |
| **Step Definition** | Java code that executes each Gherkin step                                  |
| **Glue Code**       | Package where step definitions and hooks live                              |
| **Hook**            | Code that runs before/after each scenario (browser setup/teardown)         |
| **Runner**          | A TestNG class that tells Cucumber which features to run                   |
| **Tag**             | A label like `@Login` used to filter which scenarios run                   |


