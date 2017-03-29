package com.appmoney.integrationtest.glue;

import java.util.LinkedList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class CategorySteps extends BaseSteps {

  @When("I change the category name to '(.*)'")
  public void changeCategoryName(String newName) {
    WebElement nameField = driver.findElement(By.name("name"));
    nameField.clear();
    nameField.sendKeys(newName);
    seleniumHelper.click("OK");

    acceptAlert();
  }

  @When("I change the category parent to '(.*)'")
  public void changeCategoryParent(String newParent) {
    seleniumHelper.selectOption("parentId", newParent);
    seleniumHelper.click("OK");

    acceptAlert();
  }

  @Then("I should (not ){0,1}see '(.*)' in the category list")
  public void checkVisibilityInCategoryList(String not, String name) {
    goToCategoriesScreen();
    if (not != null) {
      seleniumHelper.checkElementDoesNotExist(name);
    } else {
      seleniumHelper.getElement(name);
    }
  }

  @Then("I should (not ){0,1}see '(.*)' in the categories combo when adding a transaction")
  public void checkVisibilityInEditTransactionForm(String not, String name) {
    goToCreateTransactionForm();
    if (not != null) {
      seleniumHelper.checkElementDoesNotExist(name);
    } else {
      seleniumHelper.getElement(name);
    }
  }

  @Then("I should (not ){0,1}see subcategory '(.*)' under '(.*)' when adding a transaction")
  public void checkSubcategoryVisibilityInEditTransactionForm(String not, String categoryToFind, String expectedParent) {
    goToCreateTransactionForm();
    WebElement combo = driver.findElement(By.cssSelector("select[name=categoryId]"));
    checkParentOfCategory(combo.findElements(By.cssSelector("option")), categoryToFind, expectedParent, not == null);
  }

  @Then("I should (not ){0,1}see subcategory '(.*)' under '(.*)' in the category list")
  public void checkVisibilityForSubcategoryInCategoryList(String not, String categoryToFind, String expectedParent) {
    goToCategoriesScreen();
    WebElement body = driver.findElement(By.cssSelector("tbody"));
    checkParentOfCategory(body.findElements(By.xpath("tr/td[2]")), categoryToFind, expectedParent, not == null);
  }

  @Given("I have a category called '([^']*)'")
  @When("I created a new category called '([^']*)'")
  public void createCategory(String name) {
    goToCreateCategoryForm();

    driver.findElement(By.name("name")).sendKeys(name);
    seleniumHelper.click("OK");

    acceptAlert();
  }

  @When("I created a new category with parent called '(.*)' under '(.*)'")
  public void createCategoryWithParent(String name, String parent) {
    goToCreateCategoryForm();

    driver.findElement(By.name("name")).sendKeys(name);
    seleniumHelper.selectOption("parentId", parent);
    seleniumHelper.click("OK");

    acceptAlert();
  }

  @When("I click edit category on category '(.*)'")
  public void editCategory(String name) {
    WebElement categoryRow = seleniumHelper.getElementParent(name);
    categoryRow.findElement(By.className("edit-category")).click();
    seleniumHelper.waitForText("Editar Categoria");
  }

  @When("I go to the categories screen")
  public void goToCategoriesScreen() {
    driver.navigate().to("http://localhost:8080/categories");
    seleniumHelper.waitForText("Categorias");
  }

  public void goToCreateCategoryForm() {
    goToCategoriesScreen();
    seleniumHelper.clickElementById("new-category");
    seleniumHelper.waitForText("Criar Categoria");
  }

  @When("I click show hidden categories")
  public void showHiddenCategories() {
    seleniumHelper.clickElementById("show-hidden");
    seleniumHelper.waitForElementWithClass("tone-down");
  }

  @When("I click toggle visibility for the default category called '(.*)'")
  public void toggleDefaultCategory(String name) {
    WebElement categoryRow = seleniumHelper.getElementParent(name);
    categoryRow.findElement(By.className("toggle-default")).click();
  }

  private void checkParentOfCategory(List<WebElement> elements, String categoryToFind, String expectedParent, boolean shouldBe) {
    LinkedList<String> parents = new LinkedList<>();

    String lastCategory = null;
    for (WebElement nameElement : elements) {
      String category = nameElement.getText();

      int diff = lastCategory == null ? 0 : lastCategory.lastIndexOf("    ") - category.lastIndexOf("    ");

      if (diff < 0) {
        parents.push(lastCategory.trim());
      } else if (diff > 0){
        for (int i = 0 ; i < Math.abs(diff); i+= 4) {
          if (parents.size() > 0) {
            parents.pop();
          }
        }
      }
      lastCategory = category;

      if (category.trim().equals(categoryToFind)) {
        break;
      }
    }

    String actualParent = parents.size() == 0 ? "" : parents.pop();

    if (shouldBe) {
      if (!actualParent.equals(expectedParent)) {
        throw new RuntimeException("Parent of " + categoryToFind + " is " + actualParent);
      }
    } else {
      if (actualParent.equals(expectedParent)) {
        throw new RuntimeException("Parent of " + categoryToFind + " is " + actualParent);
      }
    }
  }

}
