package com.appmoney.integrationtest;

import java.util.concurrent.TimeUnit;

import org.assertj.core.util.Objects;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.springframework.beans.factory.annotation.Autowired;

public class SeleniumHelper {

  private static final int WAIT_TIMEOUT = 10;

  private final FluentWait<WebDriver> wait;
  private final WebDriver driver;

  @Autowired
  public SeleniumHelper(WebDriver driver) {
    this.driver = driver;
    this.wait = new FluentWait<WebDriver>(driver)
        .withTimeout(WAIT_TIMEOUT, TimeUnit.SECONDS)
        .ignoring(NoSuchElementException.class)
        .ignoring(NoAlertPresentException.class);
  }

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

  public void waitForAlert(String withText) {
    wait.until( d -> {
      Alert a = d.switchTo().alert();
      String actualText = a.getText();
      if (!Objects.areEqual(withText, actualText)) {
        a.dismiss();
        throw new RuntimeException(String.format("Expected alert with text: '%s' but got '%s'", withText, actualText));
      }
      return a;
    });
  }

  public void waitForElementWithId(String elementId) {
    wait.until(d -> d.findElement(By.id(elementId)));
  }

  public void waitForElementWithClass(String className) {
    wait.until(d -> d.findElement(By.className(className)).isDisplayed());
  }

  public void waitForLink(String linkText) {
    wait.until(d -> d.findElement(By.linkText(linkText)));
  }

  public void waitForText(String text) {
    wait.until(d -> d.findElement(By.xpath("//*[text()=\""+text+"\"]")));
  }

  public void clearAndType(String fieldName, String text) {
    WebElement nameField = driver.findElement(By.name(fieldName));
    nameField.clear();
    nameField.sendKeys(text);
  }

}
