package com.appmoney.integrationtest.glue;

import org.openqa.selenium.By;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class TransactionSteps extends BaseSteps {

  @Then("I should not see a transaction with description '(.*)' on the transaction list")
  public void checkTransactionDoesNotExistInHomeScreen(String description) {
    goToHomeScreen();
    seleniumHelper.checkElementDoesNotExist(description);
  }

  @Then("^I see a transaction with description '(.*)', category '(.*)' and amount (\\d+\\.?\\d{0,2}) on the transaction list$")
  public void checkTransactionExists(String description, String category, Float amount) {
    goToHomeScreen();
    seleniumHelper.waitForLink(description);
    seleniumHelper.waitForText(category);
    seleniumHelper.waitForText(String.format("$ %.2f", amount));
  }

  @When("I click delete transaction")
  public void clickDeleteTransaction() {
    driver.findElement(By.id("delete-transaction")).click();
    acceptAlert("Você tem certeza que quer apagar esta transação?");
    waitForHomeScreen();
  }

  @When("I select transaction with description '(.*)'")
  public void selectTransaction(String description) {
    goToHomeScreen();
    seleniumHelper.click(description);
    seleniumHelper.waitForElementWithId("delete-transaction");
  }

  @When("^I edit the transaction description to be '(.*)', category '(.*)', amount (\\d+\\.?\\d{0,2}) and date (\\d{2})/(\\d{2})/(\\d{4})$")
  public void editTransaction(String description, String category, Float amount, String month, String day, String year) {
    seleniumHelper.clearAndType("title", description);
    seleniumHelper.selectOption("categoryId", category);
    seleniumHelper.clearAndType("value", amount.toString());
    if (month!= null) {
      driver.findElement(By.name("happened")).sendKeys(month + day + year);
    }
    seleniumHelper.click("Salvar");
    acceptAlert();
  }

  @When("^I have a transaction with description '(.*)', category '(.*)' and amount (\\d+\\.?\\d{0,2})$")
  public void ensureTransaction(String description, String category, Float amount) {
    ensureTransaction(description, category, amount, null, null, null);
  }

  @When("^I have a transaction with description '(.*)', category '(.*)', amount (\\d+\\.?\\d{0,2}) and date (\\d{2})/(\\d{2})/(\\d{4})$")
  @Given("^I create a transaction with description '(.*)', category '(.*)', amount (\\d+\\.?\\d{0,2}) and date (\\d{2})/(\\d{2})/(\\d{4})$")
  public void ensureTransaction(String description, String category, Float amount, String month, String day, String year) {
    goToCreateTransactionForm();
    driver.findElement(By.name("title")).sendKeys(description);
    seleniumHelper.selectOption("categoryId", category);
    driver.findElement(By.name("value")).sendKeys(amount.toString());
    if (month!= null) {
      driver.findElement(By.name("happened")).sendKeys(month + day + year);
    }
    seleniumHelper.click("Salvar");
    acceptAlert();
  }

}
