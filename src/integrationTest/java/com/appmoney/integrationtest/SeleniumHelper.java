package com.appmoney.integrationtest;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;

public class SeleniumHelper {

  private static final int WAIT_TIMEOUT = 10;

  @Autowired
  WebDriver driver;

  public void click(String text){
    driver.findElement(By.xpath("//*[text()=\""+text+"\"]")).click();
  }

  public void selectOption(String selectName, String optionText) {
    Select select = new Select(driver.findElement(By.name(selectName)));
    for (WebElement option: select.getOptions()) {
      if (option.getText().equals(optionText)) {
        option.click();
        break;
      }
    }
  }

  public void waitForLink(String linkText) {
    new WebDriverWait(driver, WAIT_TIMEOUT).until(ExpectedConditions.visibilityOfElementLocated(By.linkText(linkText)));
  }

  public void waitForText(String text) {
    new WebDriverWait(driver, WAIT_TIMEOUT).until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()=\""+text+"\"]")));
  }

}
