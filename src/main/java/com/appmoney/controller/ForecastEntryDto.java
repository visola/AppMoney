package com.appmoney.controller;

import java.util.ArrayList;
import java.util.List;

public class ForecastEntryDto {

  private Integer id;
  private Integer forecastId;
  private String title;
  private Integer categoryId;
  private List<MonthlyForecastEntryAmountDto> monthlyAmounts = new ArrayList<>();

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Integer getForecastId() {
    return forecastId;
  }

  public void setForecastId(Integer forecastId) {
    this.forecastId = forecastId;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public Integer getCategoryId() {
    return categoryId;
  }

  public void setCategoryId(Integer categoryId) {
    this.categoryId = categoryId;
  }

  public List<MonthlyForecastEntryAmountDto> getMonthlyAmounts() {
    return monthlyAmounts;
  }

  public void setMonthlyAmounts(List<MonthlyForecastEntryAmountDto> monthlyAmounts) {
    this.monthlyAmounts = monthlyAmounts;
  }

}
