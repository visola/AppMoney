Feature: Categories

  As a user I want to be able to manage the categories I use to group my transactions.

  Background:
    Given I am logged in with user 'jdoe@test.com'
    And I have an account with name 'My Account' of type 'Checkings'

  Scenario: I hide a default category
    When I go to the categories screen
    And I click toggle visibility for the default category called 'Moradia'
    Then I should not see 'Moradia' in the category list
    And I should not see 'Moradia' in the categories combo when adding a transaction

  Scenario: I show a hidden default category
    Given I go to the categories screen
    And I click toggle visibility for the default category called 'Moradia'
    When I go to the categories screen
    And I click show hidden categories
    And I click toggle visibility for the default category called 'Moradia'
    Then I should see 'Moradia' in the category list
    And I should see 'Moradia' in the categories combo when adding a transaction

  Scenario: I create a new category
    When I go to the categories screen
    And I created a new category called 'Bills'
    Then I should see 'Bills' in the category list
    And I should see 'Bills' in the categories combo when adding a transaction

  Scenario: I create a new category with a parent
    Given I created a new category called 'Bills'
    When I go to the categories screen
    And I created a new category with parent called 'Utilities' under 'Bills'
    Then I should see subcategory 'Utilities' under 'Bills' in the category list
    And I should see subcategory 'Utilities' under 'Bills' when adding a transaction

  Scenario: I edit an existing category
    Given I created a new category called 'Bills'
    When I go to the categories screen
    And I click edit category on category 'Bills'
    And I change the category name to 'Utilities'
    Then I should see 'Utilities' in the category list
    And I should see 'Utilities' in the categories combo when adding a transaction
    And I should not see 'Bills' in the category list
    And I should not see 'Bills' in the categories combo when adding a transaction

  Scenario: I edit change a category parent
    Given I created a new category called 'Bills'
    And I created a new category called 'House'
    And I created a new category with parent called 'Utilities' under 'Bills'
    When I go to the categories screen
    And I click edit category on category 'Utilities'
    And I change the category parent to 'House'
    Then I should see subcategory 'Utilities' under 'House' in the category list
    And I should see subcategory 'Utilities' under 'House' when adding a transaction
    And I should not see subcategory 'Utilities' under 'Bills' in the category list
    And I should not see subcategory 'Utilities' under 'Bills' when adding a transaction
