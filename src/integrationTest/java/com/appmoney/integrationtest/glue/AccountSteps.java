package com.appmoney.integrationtest.glue;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;

import com.appmoney.integrationtest.SeleniumHelper;

import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class AccountSteps extends BaseGlue {

  @Autowired
  SeleniumHelper seleniumHelper;

  @Autowired
  WebDriver driver;

  @Then("^I should see an account with name '(.*)'$")
  public void checkAccountExists(String accountName) {
    driver.navigate().to("http://localhost:8080/");
  }

  @When("^I fill account form with name '(.*)' of type '(.*)'$")
  public void fillAccountForm(String name, String type) {
    driver.findElement(By.name("name")).sendKeys(name);
    driver.findElement(By.name("initialBalance")).sendKeys("0");
    driver.findElement(By.name("initialBalanceDate")).sendKeys("08102015");
    seleniumHelper.selectOption("type", type);
    seleniumHelper.click("Save");
  }

  @When("^I go to the create account screen$")
  public void goToCreateAccountScreen() {
    seleniumHelper.click("Create Account");
    seleniumHelper.waitForText("Save");
  }

}
