Feature: Transactions

  As a user I want to be able to manage my transactions.

  Background:
    Given I am logged in with user 'jdoe@test.com'
    And I have a category called 'My Category'
    And I have a category called 'Another Category'

  Scenario: I create a transaction after initial account balance
   Given I have an account with name 'My Account', type 'Conta Corrente', initial balance 1000.00 and initial balance date 03/01/2017
   When I create a transaction with description 'My transaction', category 'My Category', amount 100.00 and date 03/02/2017
   Then I see a transaction with description 'My transaction', category 'My Category' and amount 100.00 on the transaction list
   And I see account 'My Account' with balance 900.00

  Scenario: I create a transaction before initial account balance
   Given I have an account with name 'My Account', type 'Conta Corrente', initial balance 1000.00 and initial balance date 03/01/2017
   When I create a transaction with description 'My transaction', category 'My Category', amount 100.00 and date 02/28/2017
   Then I see a transaction with description 'My transaction', category 'My Category' and amount 100.00 on the transaction list
   And I see account 'My Account' with balance 1000.00

  Scenario: I edit an existing transaction
    Given I have an account with name 'My Account', type 'Conta Corrente', initial balance 1000.00 and initial balance date 03/01/2017
    And I have a transaction with description 'My transaction', category 'My Category', amount 100.00 and date 02/28/2017
    When I select transaction with description 'My transaction'
    And I edit the transaction description to be 'Another Name', category 'Another Category', amount 50.00 and date 03/02/2017
    Then I see a transaction with description 'Another Name', category 'Another Category' and amount 50.00 on the transaction list
    And I see account 'My Account' with balance 950.00

  Scenario: I delete an existing transaction
    Given I have an account with name 'My Account', type 'Conta Corrente', initial balance 1000.00 and initial balance date 03/01/2017
    And I create a transaction with description 'My transaction', category 'My Category', amount 100.00 and date 03/02/2017
    When I select transaction with description 'My transaction'
    And I click delete transaction
    Then I should not see a transaction with description 'A transaction' on the transaction list
    And I should not see any transactions in the report screen
    And I see account 'My Account' with balance 1000.00
