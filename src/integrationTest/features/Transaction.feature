Feature: Transactions

  As a user I want to be able to manage my transactions.

  Background:
    Given I am logged in with user 'jdoe@test.com'
    And I have an account with name 'My Account' of type 'Checkings'

  Scenario: I delete an existing transction
    Given I have a transaction with description 'A transaction' and category 'Moradia' and amount '100'
    When I select transaction with description 'A transaction'
    And I click delete trnasaction
    Then I should not see a transaction with description 'A transaction' in the home screen
    And I should not see any transactions in the report screen
