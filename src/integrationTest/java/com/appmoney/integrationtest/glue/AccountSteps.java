package com.appmoney.integrationtest.glue;

import org.openqa.selenium.By;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class AccountSteps extends BaseGlue {

  @Then("^I should see an account with name '(.*)'$")
  public void checkAccountExists(String accountName) {
    goToHomeScreen();
    seleniumHelper.waitForText(accountName);
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

  @When("^I delete the account$")
  public void deleteAccount() {
    driver.findElement(By.id("delete-account")).click();
    acceptAlert();
    waitForHomeScreen();
  }

  @When("^I fill account form with name '(.*)' of type '(.*)'$")
  public void fillAccountForm(String name, String type) {
    driver.findElement(By.name("name")).sendKeys(name);
    driver.findElement(By.name("initialBalance")).sendKeys("0");
    driver.findElement(By.name("initialBalanceDate")).sendKeys("08102015");
    seleniumHelper.selectOption("type", type);
    seleniumHelper.click("Salvar");

    acceptAlert();
  }

  @When("^I go to the create account screen$")
  public void goToCreateAccountScreen() {
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
