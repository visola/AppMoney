Feature: Accounts

  As I user I want to have an account where I can put and take money from.

  Scenario: I create a new account
    Given I am logged in with user 'jdoe@test.com'
    When I go to the create account screen
    And I save account with name 'My Account' and type 'Conta Corrente'
    Then I should see an account with name 'My Account'

  Scenario: I edit an existing account
    Given I am logged in with user 'jdoe@test.com'
    And I have an account with name 'My Account' of type 'Conta Corrent'
    When I go to the edit account screen to edit account with name 'My Account'
    And I save account with name 'Another Name', type 'Poupança', initial balance 1000.50 and initial balance date 08/15/2015
    Then I should see an account with name 'Another Name' and initial balance 1000.50

  Scenario: I delete an account
    Given I am logged in with user 'jdoe@test.com'
    And I have an account with name 'My Account' of type 'Conta Corrente'
    When I go to the edit account screen to edit account with name 'My Account'
    And I delete the account
    Then I should not see the account with name 'My Account'
