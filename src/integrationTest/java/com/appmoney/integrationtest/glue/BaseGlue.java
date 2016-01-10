package com.appmoney.integrationtest.glue;

import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.ConfigFileApplicationContextInitializer;
import org.springframework.test.context.ContextConfiguration;

import com.appmoney.integrationtest.IntegrationTestConfiguration;
import com.appmoney.integrationtest.SeleniumHelper;

@ContextConfiguration(classes={IntegrationTestConfiguration.class}, initializers = ConfigFileApplicationContextInitializer.class)
public class BaseGlue {

  @Autowired
  protected SeleniumHelper seleniumHelper;

  @Autowired
  protected WebDriver driver;

  public void acceptAlert() {
    seleniumHelper.waitForAlert();
    driver.switchTo().alert().accept();
  }

  public void goToCreateTransactionForm() {
    driver.navigate().to("http://localhost:8080/debit/1");
    seleniumHelper.waitForText("Título:");
  }

}
