package com.appmoney.model;

import java.math.BigDecimal;
import java.util.Date;

public class Account {

  private Integer id;
  private Integer owner;
  private BigDecimal initialBalance;
  private Date initialBalanceDate;
  private BigDecimal balance;
  private Date created;
  private Integer createdBy;
  private Date updated;
  private Integer updatedBy;
  private AccountType type;

  public BigDecimal getBalance() {
    return balance;
  }

  public Date getCreated() {
    return created;
  }

  public Integer getCreatedBy() {
    return createdBy;
  }

  public Integer getId() {
    return id;
  }

  public BigDecimal getInitialBalance() {
    return initialBalance;
  }

  public Date getInitialBalanceDate() {
    return initialBalanceDate;
  }

  public Integer getOwner() {
    return owner;
  }

  public Date getUpdated() {
    return updated;
  }

  public Integer getUpdatedBy() {
    return updatedBy;
  }

  public void setBalance(BigDecimal balance) {
    this.balance = balance;
  }

  public void setCreated(Date created) {
    this.created = created;
  }

  public void setCreatedBy(Integer createdBy) {
    this.createdBy = createdBy;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public void setInitialBalance(BigDecimal initialBalance) {
    this.initialBalance = initialBalance;
  }

  public void setInitialBalanceDate(Date initialBalanceDate) {
    this.initialBalanceDate = initialBalanceDate;
  }

  public void setOwner(Integer owner) {
    this.owner = owner;
  }

  public void setUpdated(Date updated) {
    this.updated = updated;
  }

  public void setUpdatedBy(Integer updatedBy) {
    this.updatedBy = updatedBy;
  }

  public AccountType getType() {
    return type;
  }

  public void setType(AccountType type) {
    this.type = type;
  }

}
