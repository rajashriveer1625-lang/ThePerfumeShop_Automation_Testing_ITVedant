@MenuNavigation @Smoke
Feature: Menu Navigation
  As a visitor
  I want to navigate using the main menu
  So that I can browse different sections of the website

  Scenario: Navigate through main menu links
    Given I open The Perfume Shop website
    When I click on "Perfumes" in the navigation menu
    Then I should be on the products page
    When I click on "Collections" in the navigation menu
    Then I should be on the categories page
    When I click on "Our Story" in the navigation menu
    Then I should be on the about page
