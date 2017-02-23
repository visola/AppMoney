package com.appmoney.model;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Entity
public class MonthlyForecastEntryAmount {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @NotNull
  @Min(1)
  @Max(12)
  private Integer month;

  @NotNull
  private Integer year;

  @NotNull
  private BigDecimal amount;

  @ManyToOne
  ForecastEntry forecastEntry;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

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

  public ForecastEntry getForecastEntry() {
    return forecastEntry;
  }

  public void setForecastEntry(ForecastEntry forecastEntry) {
    this.forecastEntry = forecastEntry;
  }

}
