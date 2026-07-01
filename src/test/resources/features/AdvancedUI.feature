@AdvancedUI
Feature: Advanced UI Interactions
  As a tester
  I want to exercise advanced Selenium concepts
  So that the automation framework meets project requirements

  @Skip
  Scenario: Search, hover, dropdown, windows, home navigation, and validation
    Given I open The Perfume Shop website
    When I hover over "Perfumes" in the navigation menu using Actions
    And I open the products page
    And I search for "Oud" in the product search
    And I filter products by "Oud Collection" category
    Then the product results should contain "Oud"
    When I open "Our Story" in a new browser tab and switch back
    And I click the logo to return to the homepage
    Then the homepage hero title should be visible

  @Smoke
  Scenario: Invalid login shows warning and Excel data can be read
    Given I open The Perfume Shop website
    When I navigate to the login page
    And I attempt login with invalid credentials from Excel
    Then I should see a login warning or remain on login page

  @Smoke
  Scenario: Verify Cash on Delivery checkbox state on checkout
    Given I open The Perfume Shop website
    And I login with sample user credentials
    And I add the default product to cart
    When I verify Cash on Delivery payment option is selectable
    Then the Cash on Delivery option should be selected
