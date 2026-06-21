@AddToCart @Smoke
Feature: Add to Cart
  As a logged-in user
  I want to add a product to my cart
  So that I can purchase it later

  Scenario: Login and add product to cart
    Given I open The Perfume Shop website
    And I login with sample user credentials
    When I add "Midnight Oud" product to the cart
    And I open the cart page
    Then the cart should contain "Midnight Oud"
    When I proceed to checkout
    Then the checkout page should be displayed
