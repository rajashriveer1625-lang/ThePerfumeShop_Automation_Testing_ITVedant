@Register @Smoke
Feature: Register and Login
  As a new user
  I want to register on the website
  So that I can create an account and login

  Scenario: Register a new user and login
    Given I open The Perfume Shop website
    When I navigate to the register page
    And I register a new user with valid details
    And I login with the newly registered user
    Then I should be redirected to the products page
