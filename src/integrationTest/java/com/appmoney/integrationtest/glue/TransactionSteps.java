package com.appmoney.integrationtest.glue;

import org.openqa.selenium.By;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class TransactionSteps extends BaseGlue {

  @Then("I should not see a transaction with description '(.*)' in the home screen")
  public void checkTransactionDoesNotExistInHomeScreen(String description) {
    goToHomeScreen();
    seleniumHelper.checkElementDoesNotExist(description);
  }

  @When("I click delete trnasaction")
  public void clickDeleteTransaction() {
    driver.findElement(By.id("delete-transaction")).click();
    acceptAlert();
    waitForHomeScreen();
  }

  @When("I select transaction with description '(.*)'")
  public void selectTransaction(String description) {
    goToHomeScreen();
    seleniumHelper.click(description);
    seleniumHelper.waitForElementWithId("delete-transaction");
  }

  @Given("I have a transaction with description '(.*)' and category '(.*)' and amount '(.*)'")
  public void transactionExist(String description, String category, Integer amount) throws InterruptedException {
    goToCreateTransactionForm();
    driver.findElement(By.name("title")).sendKeys(description);
    seleniumHelper.selectOption("categoryId", category);
    driver.findElement(By.name("value")).sendKeys(amount.toString());
    seleniumHelper.click("Salvar");
    acceptAlert();
  }

}
