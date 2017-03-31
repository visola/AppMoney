package com.appmoney.integrationtest.glue;

import cucumber.api.java.en.Then;

public class ReportSteps extends BaseSteps {

  @Then("I should not see any transactions in the report screen")
  public void checkNoTransactions() {
    goToReportsScreen();
    seleniumHelper.getElement("Nenhum dado para mostrar.");
  }

}
