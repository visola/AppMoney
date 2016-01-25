Feature: Accounts

  As I user I want to have an account where I can put and take money from.

  Scenario: I create a new account
    Given I am logged in with user 'jdoe@test.com'
    When I go to the create account screen
    And I fill account form with name 'My Account' of type 'Checkings'
    Then I should see an account with name 'My Account'

  Scenario: I delete an account
    Given I am logged in with user 'jdoe@test.com'
    And I have an account with name 'My Account' of type 'Checkings'
    When I go to the edit account screen to edit account with name 'My Account'
    And I delete the account
    Then I should not see the account with name 'My Account'
