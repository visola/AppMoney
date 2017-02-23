package com.appmoney.controller;

import java.math.BigDecimal;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class MonthlyForecastEntryAmountDto {

  @NotNull
  @Min(1)
  @Max(12)
  private Integer month;

  @NotNull
  private Integer year;

  @NotNull
  private BigDecimal amount;

  public Integer getMonth() {
    return month;
  }

  public void setMonth(Integer month) {
    this.month = month;
  }

  public Integer getYear() {
    return year;
  }

  public void setYear(Integer year) {
    this.year = year;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public void setAmount(BigDecimal amount) {
    this.amount = amount;
  }

}
