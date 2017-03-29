package com.appmoney.integrationtest;

import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
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

  public void checkElementDoesNotExist(String text) {
    try {
      WebElement element = getElement(text);
      throw new RuntimeException("Element should not be present but was found: '" + element.toString() + "'");
    } catch (NoSuchElementException e) {
      // This is what we want
    }
  }

  public void click(String text){
    getElement(text).click();
  }

  public void clickElementById(String id) {
    driver.findElement(By.id(id)).click();
  }

  public WebElement getElement(String text) {
    return driver.findElement(By.xpath("//*[contains(text(),\""+text+"\")]"));
  }

  public WebElement getElementUnder(WebElement parent, String text) {
    return parent.findElement(By.xpath("*[contains(text(),\""+text+"\")]"));
  }

  public WebElement getElementParent(String text) {
    return getElement(text).findElement(By.xpath(".."));
  }

  public void selectOption(String selectName, String optionText) {
    Select select = new Select(driver.findElement(By.name(selectName)));
    boolean selected = false;
    for (WebElement option: select.getOptions()) {
      if (option.getText().contains(optionText)) {
        option.click();
        selected = true;
        break;
      }
    }
    if (!selected) throw new RuntimeException("Option '"+optionText+"' not found in select: "+selectName);
  }

  public void waitForAlert() {
    new WebDriverWait(driver, WAIT_TIMEOUT)
      .ignoring(NoAlertPresentException.class)
      .until(ExpectedConditions.alertIsPresent());
  }

  public void waitForElementWithId(String elementId) {
    new WebDriverWait(driver, WAIT_TIMEOUT)
    .until(ExpectedConditions.visibilityOfElementLocated(By.id(elementId)));
  }

  public void waitForElementWithClass(String className) {
    new WebDriverWait(driver, WAIT_TIMEOUT)
    .until(ExpectedConditions.visibilityOfElementLocated(By.className(className)));
  }

  public void waitForLink(String linkText) {
    new WebDriverWait(driver, WAIT_TIMEOUT)
      .until(ExpectedConditions.visibilityOfElementLocated(By.linkText(linkText)));
  }

  public void waitForText(String text) {
    new WebDriverWait(driver, WAIT_TIMEOUT)
      .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()=\""+text+"\"]")));
  }

  public void clearAndType(String fieldName, String text) {
    WebElement nameField = driver.findElement(By.name(fieldName));
    nameField.clear();
    nameField.sendKeys(text);
  }

}
