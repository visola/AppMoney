package com.appmoney.controller;

import java.util.ArrayList;
import java.util.List;

import com.appmoney.model.Category;
import com.appmoney.model.Forecast;

public class ForecastEntryDto {

  private Integer id;
  private Forecast forecast;
  private String title;
  private Category category;
  private List<MonthlyForecastEntryAmountDto> monthlyAmounts = new ArrayList<>();

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public Forecast getForecast() {
    return forecast;
  }

  public void setForecast(Forecast forecast) {
    this.forecast = forecast;
  }

  public Category getCategory() {
    return category;
  }

  public void setCategory(Category category) {
    this.category = category;
  }

  public List<MonthlyForecastEntryAmountDto> getMonthlyAmounts() {
    return monthlyAmounts;
  }

  public void setMonthlyAmounts(List<MonthlyForecastEntryAmountDto> monthlyAmounts) {
    this.monthlyAmounts = monthlyAmounts;
  }

}
