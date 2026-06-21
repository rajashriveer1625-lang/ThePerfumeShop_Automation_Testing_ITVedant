@Checkout @Smoke
Feature: Checkout
  As a logged-in user
  I want to complete the checkout process
  So that I can place an order

  Scenario: Complete checkout with Cash on Delivery
    Given I open The Perfume Shop website
    And I login with sample user credentials
    When I add "Midnight Oud" product to the cart
    And I open the cart page
    Then the cart should contain "Midnight Oud"
    And I proceed to checkout
    And I fill shipping details with valid information
    And I select Cash on Delivery payment method
    And I place the order
    Then the order success page should be displayed
