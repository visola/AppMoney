package com.appmoney.integrationtest.glue;

import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;

import com.appmoney.integrationtest.SeleniumHelper;

import cucumber.api.java.en.Given;

public class LoginSteps extends BaseGlue{

  @Autowired
  SeleniumHelper seleniumHelper;

  @Autowired
  WebDriver driver;

  @Given("^I am logged in with user '(.*)'$")
  public void loginAsUser(String username) {
    driver.navigate().to("http://localhost:8080/testLogin?username="+username);
    seleniumHelper.waitForLink("Criar Conta");
  }

}
