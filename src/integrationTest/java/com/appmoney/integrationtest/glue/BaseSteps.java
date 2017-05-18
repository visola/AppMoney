package com.appmoney.integrationtest.glue;

import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.ConfigFileApplicationContextInitializer;
import org.springframework.test.context.ContextConfiguration;

import com.appmoney.integrationtest.IntegrationTestConfiguration;
import com.appmoney.integrationtest.SeleniumHelper;

@ContextConfiguration(classes={IntegrationTestConfiguration.class}, initializers = ConfigFileApplicationContextInitializer.class)
public class BaseSteps {

  @Autowired
  protected SeleniumHelper seleniumHelper;

  @Autowired
  protected WebDriver driver;

  public void acceptAlert() {
    acceptAlert("Dados salvos corretamente!");
  }

  public void acceptAlert(String withText) {
    seleniumHelper.waitForAlert(withText);
    driver.switchTo().alert().accept();
  }

  public void goToCreateTransactionForm() {
    driver.navigate().to("http://localhost:8080/debit/1");
    seleniumHelper.waitForText("Título:");
  }

  public void goToHomeScreen() {
    driver.navigate().to("http://localhost:8080/");
    waitForHomeScreen();
  }

  public void goToReportsScreen() {
    driver.navigate().to("http://localhost:8080/reports/category");
    seleniumHelper.waitForText("Filtro");
  }

  public void waitForHomeScreen() {
    seleniumHelper.waitForText("Criar Conta");
  }

}
