package com.appmoney.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.validation.constraints.NotNull;

public class Transaction {

  private Integer id;
  @NotNull private BigDecimal value;
  @NotNull private Date happened;
  @NotNull private Integer fromAccountId;
  private Integer toAccountId;
  @NotNull private Integer categoryId;
  private Date created;
  private Integer createdBy;
  private Date updated;
  private Integer updatedBy;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public BigDecimal getValue() {
    return value;
  }

  public void setValue(BigDecimal value) {
    this.value = value;
  }

  public Date getHappened() {
    return happened;
  }

  public void setHappened(Date happened) {
    this.happened = happened;
  }

  public Integer getFromAccountId() {
    return fromAccountId;
  }

  public void setFromAccountId(Integer fromAccountId) {
    this.fromAccountId = fromAccountId;
  }

  public Integer getToAccountId() {
    return toAccountId;
  }

  public void setToAccountId(Integer toAccountId) {
    this.toAccountId = toAccountId;
  }

  public Integer getCategoryId() {
    return categoryId;
  }

  public void setCategoryId(Integer categoryId) {
    this.categoryId = categoryId;
  }

  public Date getCreated() {
    return created;
  }

  public void setCreated(Date created) {
    this.created = created;
  }

  public Integer getCreatedBy() {
    return createdBy;
  }

  public void setCreatedBy(Integer createdBy) {
    this.createdBy = createdBy;
  }

  public Date getUpdated() {
    return updated;
  }

  public void setUpdated(Date updated) {
    this.updated = updated;
  }

  public Integer getUpdatedBy() {
    return updatedBy;
  }

  public void setUpdatedBy(Integer updatedBy) {
    this.updatedBy = updatedBy;
  }

}
