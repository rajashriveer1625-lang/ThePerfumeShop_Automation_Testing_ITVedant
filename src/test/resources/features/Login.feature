@Login @Smoke
Feature: Login
  As a registered user
  I want to login to the website
  So that I can access my account and shop

  Scenario: Login with sample credentials
    Given I open The Perfume Shop website
    When I navigate to the login page
    And I login with sample user credentials
    Then I should be redirected to the products page
