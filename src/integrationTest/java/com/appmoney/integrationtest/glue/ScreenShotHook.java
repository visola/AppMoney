package com.appmoney.integrationtest.glue;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.springframework.beans.factory.annotation.Autowired;

import cucumber.api.Scenario;
import cucumber.api.java.After;

public class ScreenShotHook {

  @Autowired
  WebDriver driver;

  @After
  public void embedScreenshot(Scenario scenario) {
    if (scenario.isFailed()) {
      try {
        byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
        scenario.embed(screenshot, "image/png");
      } catch (WebDriverException wde) {
        System.err.println(wde.getMessage());
      } catch (ClassCastException cce) {
        cce.printStackTrace();
      }
    }
  }

}
