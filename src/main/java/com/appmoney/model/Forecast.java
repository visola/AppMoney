package com.appmoney.model;

import java.util.Calendar;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Entity
public class Forecast {

  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Id
  private Integer id;

  private Calendar created;
  private Calendar updated;

  @ManyToOne
  private User createdBy;

  @ManyToOne
  private User updatedBy;

  @NotNull
  @Min(1)
  @Max(28)
  private Integer startDayOfMonth = 1;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Calendar getCreated() {
    return created;
  }

  public void setCreated(Calendar created) {
    this.created = created;
  }

  public Calendar getUpdated() {
    return updated;
  }

  public void setUpdated(Calendar updated) {
    this.updated = updated;
  }

  public User getCreatedBy() {
    return createdBy;
  }

  public void setCreatedBy(User createdBy) {
    this.createdBy = createdBy;
  }

  public User getUpdatedBy() {
    return updatedBy;
  }

  public void setUpdatedBy(User updatedBy) {
    this.updatedBy = updatedBy;
  }

  public Integer getStartDayOfMonth() {
    return startDayOfMonth;
  }

  public void setStartDayOfMonth(Integer startDayOfMonth) {
    this.startDayOfMonth = startDayOfMonth;
  }

}
