Feature: US-005 - Homepage SHOP NOW link test

  Scenario: User should see and interact with SHOP NOW links
    Given The user navigates to the homepage
    And Closes the cookie banner if it appears
    Then The page should display 4 SHOP NOW links
    When The user clicks each SHOP NOW link one by one and verifies the URL
