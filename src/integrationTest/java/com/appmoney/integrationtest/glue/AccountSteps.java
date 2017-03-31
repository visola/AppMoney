package com.appmoney.integrationtest.glue;

import org.openqa.selenium.By;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class AccountSteps extends BaseSteps {

  @Then("^I should see an account with name '(.*)'$")
  public void checkAccountExists(String accountName) {
    checkAccountExists(accountName, null);
  }

  @Then("^I see account '(.*)' with balance (\\d+\\.?\\d{0,2})$")
  public void checkAccountBalance(String accountName, Float initialBalance) {
    checkAccountExists(accountName, initialBalance);
  }

  @Then("^I should see an account with name '(.*)' and initial balance (\\d+\\.?\\d{0,2})$")
  public void checkAccountExists(String accountName, Float initialBalance) {
    goToHomeScreen();
    seleniumHelper.waitForText(accountName);
    if (initialBalance != null) {
      seleniumHelper.waitForText(String.format("$ %.2f", initialBalance));
    }
  }

  @Then("^I should not see the account with name '(.*)'$")
  public void checkAccountDoesNotExist(String accountName) {
    goToHomeScreen();
    seleniumHelper.checkElementDoesNotExist(accountName);
  }

  @Given("^I have an account with name '(.*)' of type '(.*)'$")
  public void createAccount(String name, String type) {
    goToCreateAccountScreen();
    fillAccountForm(name, type);
    checkAccountExists(name);
  }

  @Given("^I have an account with name '(.*)', type '(.*)', initial balance (\\d+\\.?\\d{0,2}) and initial balance date (\\d{2})/(\\d{2})/(\\d{4})$")
  public void accountExists(String name, String type, Float initialBalance, String month, String day, String year) {
    goToCreateAccountScreen();
    seleniumHelper.clearAndType("name", name);
    seleniumHelper.clearAndType("initialBalance", Float.toString(initialBalance));
    driver.findElement(By.name("initialBalanceDate")).sendKeys(month + day + year);
    seleniumHelper.selectOption("type", type);
    seleniumHelper.click("Salvar");
    acceptAlert();

    checkAccountExists(name, initialBalance);
  }

  @When("^I delete the account$")
  public void deleteAccount() {
    driver.findElement(By.id("delete-account")).click();
    acceptAlert();
    waitForHomeScreen();
  }

  @When("^I edit the account setting name to '(.+)' and initial balance to (\\d+\\.?\\d{0,2})$")
  public void editAccount(String name, float initialBalance) {
    driver.findElement(By.name("name")).sendKeys(name);
  }

  @When("^I save account with name '(.*)' and type '(.*)'$")
  public void fillAccountForm(String name, String type) {
    fillAccountForm(name, type, 0.0f, "08", "13", "2015");
  }

  @When("^I save account with name '(.*)', type '(.*)', initial balance (\\d+\\.?\\d+) and initial balance date (\\d{2})/(\\d{2})/(\\d{4})$")
  public void fillAccountForm(String name, String type, Float initialBalance, String month, String day, String year) {
    seleniumHelper.clearAndType("name", name);
    seleniumHelper.clearAndType("initialBalance", Float.toString(initialBalance));
    driver.findElement(By.name("initialBalanceDate")).sendKeys(month + day + year);
    seleniumHelper.selectOption("type", type);
    seleniumHelper.click("Salvar");

    acceptAlert();
  }

  @When("^I go to the create account screen$")
  public void goToCreateAccountScreen() {
    goToHomeScreen();
    seleniumHelper.click("Criar Conta");
    seleniumHelper.waitForText("Salvar");
  }

  @When("^I go to the edit account screen to edit account with name '(.*)'$")
  public void goToEditAccountScreen(String accountName) {
    goToHomeScreen();
    seleniumHelper.click(accountName);
    seleniumHelper.waitForText("Editar Conta");
  }

}
