@MyAccount @Smoke
Feature: My Account
  As a logged-in user
  I want to view my account page
  So that I can see my profile and order history

  Scenario: View My Account page after login
    Given I open The Perfume Shop website
    And I login with sample user credentials
    When I navigate to My Account page
    Then the My Account page should be displayed
    And the Order History section should be visible
