package com.appmoney.integrationtest.glue;

import org.openqa.selenium.By;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class AccountSteps extends BaseGlue {

  @Given("^I have an account with name '(.*)' of type '(.*)'$")
  public void createAccount(String name, String type) {
    goToCreateAccountScreen();
    fillAccountForm(name, type);
    checkAccountExists(name);
  }

  @Then("^I should see an account with name '(.*)'$")
  public void checkAccountExists(String accountName) {
    driver.navigate().to("http://localhost:8080/");
    seleniumHelper.waitForText(accountName);
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

}
