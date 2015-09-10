Feature: Accounts

  As I user I want to have an account where I can put and take money from.

  Scenario: I create a new account
    Given I am logged in with user 'jdoe@test.com'
    When I go to the create account screen
    And I fill account form with name 'My Account' of type 'Checkings'
    Then I should see an account with name 'My Account'
