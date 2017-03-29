package com.appmoney.integrationtest.glue;

import cucumber.api.java.en.Given;

public class NavigationSteps extends BaseSteps {

  @Given("I am at the home screen")
  public void atHomeScreen() {
    goToHomeScreen();
  }

}
