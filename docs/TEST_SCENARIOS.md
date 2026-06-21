# Test Scenarios

Detailed walkthrough of each test scenario in plain English.

---

## 1. Home Page Test

**Runner:** `HomePageRunner`  
**Feature:** `HomePage.feature`  
**Tag:** `@HomePage`

### Scenario: Open site and verify homepage is visible

| Step | Action | What Happens |
|------|--------|-------------|
| Given | Open The Perfume Shop website | Browser navigates to https://perfume-demov-uksm.vercel.app/ |
| Then | Homepage hero title should be visible | Verifies `[data-testid='hero-title']` is displayed |
| And | Homepage subtitle should be visible | Verifies `[data-testid='hero-subtitle']` is displayed |

**Expected Result:** Homepage loads with "ThePerfumeShop" title and subtitle text visible.

---

## 2. Login Test

**Runner:** `LoginRunner`  
**Feature:** `Login.feature`  
**Tag:** `@Login`

### Scenario: Login with sample credentials

| Step | Action | What Happens |
|------|--------|-------------|
| Given | Open The Perfume Shop website | Browser opens homepage |
| When | Navigate to the login page | Goes to `/login` |
| And | Login with sample user credentials | Enters `abc@gmail.com` / `12345678` and clicks Sign in |
| Then | Should be redirected to products page | Verifies URL contains `/products` |

**Expected Result:** User is logged in and redirected to the products listing page.

**Prerequisite:** User `abc@gmail.com` must be registered on the live site.

---

## 3. Register Test

**Runner:** `RegisterRunner`  
**Feature:** `Register.feature`  
**Tag:** `@Register`

### Scenario: Register a new user and login

| Step | Action | What Happens |
|------|--------|-------------|
| Given | Open The Perfume Shop website | Browser opens homepage |
| When | Navigate to the register page | Goes to `/register` |
| And | Register a new user with valid details | Fills form with auto-generated unique email |
| And | Login with the newly registered user | Logs in using the email/password just created |
| Then | Should be redirected to products page | Verifies URL contains `/products` |

**Expected Result:** New user is created and can login successfully.

**Note:** Each run generates a unique email (e.g., `autotest_a1b2c3d4@mail.com`) to avoid duplicate email errors.

---

## 4. Menu Navigation Test

**Runner:** `MenuNavigationRunner`  
**Feature:** `MenuNavigation.feature`  
**Tag:** `@MenuNavigation`

### Scenario: Navigate through main menu links

| Step | Action | What Happens |
|------|--------|-------------|
| Given | Open The Perfume Shop website | Browser opens homepage |
| When | Click "Perfumes" in navigation menu | Clicks header link |
| Then | Should be on products page | Verifies URL contains `/products` |
| When | Click "Collections" in navigation menu | Clicks header link |
| Then | Should be on categories page | Verifies URL contains `/categories` |
| When | Click "Our Story" in navigation menu | Clicks header link |
| Then | Should be on about page | Verifies URL contains `/about` |

**Expected Result:** All three main navigation links work correctly.

---

## 5. Add to Cart Test

**Runner:** `AddToCartRunner`  
**Feature:** `AddToCart.feature`  
**Tag:** `@AddToCart`

### Scenario: Login and add product to cart

| Step | Action | What Happens |
|------|--------|-------------|
| Given | Open The Perfume Shop website | Browser opens homepage |
| And | Login with sample user credentials | Logs in as `abc@gmail.com` |
| When | Add "Midnight Oud" product to the cart | Opens `/products/1`, clicks Add to Cart |
| And | Open the cart page | Navigates to `/cart` |
| Then | Cart should contain "Midnight Oud" | Verifies product name appears in cart |
| When | Proceed to checkout | Clicks Checkout button |
| Then | Checkout page should be displayed | Verifies checkout title is visible |

**Expected Result:** Product is added to cart and checkout page loads.

**Prerequisite:** User `abc@gmail.com` must be registered on the live site.

---

## 6. Checkout Test

**Runner:** `CheckoutRunner`  
**Feature:** `Checkout.feature`  
**Tag:** `@Checkout`

### Scenario: Complete checkout with Cash on Delivery

| Step | Action | What Happens |
|------|--------|-------------|
| Given | Open The Perfume Shop website | Browser opens homepage |
| And | Login with sample user credentials | Logs in as `abc@gmail.com` |
| When | Add "Midnight Oud" product to the cart | Opens product and adds to cart |
| And | Open the cart page | Navigates to `/cart` |
| And | Proceed to checkout | Clicks Checkout |
| And | Fill shipping details with valid information | Enters John Doe, 123 Main St, Mumbai, 400001 |
| And | Select Cash on Delivery payment method | Clicks COD radio button |
| And | Place the order | Clicks Place Order button |
| Then | Order success page should be displayed | Verifies "Order Placed Successfully!" |

**Expected Result:** Order is placed and success page is shown.

**Prerequisite:** User `abc@gmail.com` must be registered on the live site.

---

## 7. My Account Test

**Runner:** `MyAccountRunner`  
**Feature:** `MyAccount.feature`  
**Tag:** `@MyAccount`

### Scenario: View My Account page after login

| Step | Action | What Happens |
|------|--------|-------------|
| Given | Open The Perfume Shop website | Browser opens homepage |
| And | Login with sample user credentials | Logs in as `abc@gmail.com` |
| When | Navigate to My Account page | Goes to `/account` |
| Then | My Account page should be displayed | Verifies "My Account" heading |
| And | Order History section should be visible | Verifies "Order History" heading |

**Expected Result:** Account page shows profile and order history sections.

**Prerequisite:** User `abc@gmail.com` must be registered on the live site.

---

## Master Runner

**Runner:** `MasterRunner`  
**Tag:** `@Smoke`

Runs all 7 scenarios above in a single execution. Use this for a full regression test.

```bash
mvn clean test -Dtest=MasterRunner
```
