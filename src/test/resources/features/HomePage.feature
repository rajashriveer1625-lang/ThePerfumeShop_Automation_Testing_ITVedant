@HomePage @Smoke
Feature: Home Page
  As a visitor
  I want to open the Perfume Shop website
  So that I can see the homepage is loaded correctly

  Scenario: Open site and verify homepage is visible
    Given I open The Perfume Shop website
    Then the homepage hero title should be visible
    And the homepage subtitle should be visible
