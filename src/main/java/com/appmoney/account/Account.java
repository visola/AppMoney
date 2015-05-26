package com.appmoney.account;

import java.math.BigDecimal;
import java.util.Date;

public class Account {

  private int id;
  private String owner;
  private BigDecimal initialBalance;
  private Date initialBalanceDate;
  private BigDecimal balance;
  private Date created;
  private int createdBy;
  private Date updated;
  private int updatedBy;

  public BigDecimal getBalance() {
    return balance;
  }

  public Date getCreated() {
    return created;
  }

  public int getCreatedBy() {
    return createdBy;
  }

  public int getId() {
    return id;
  }

  public BigDecimal getInitialBalance() {
    return initialBalance;
  }

  public Date getInitialBalanceDate() {
    return initialBalanceDate;
  }

  public String getOwner() {
    return owner;
  }

  public Date getUpdated() {
    return updated;
  }

  public int getUpdatedBy() {
    return updatedBy;
  }

  public void setBalance(BigDecimal balance) {
    this.balance = balance;
  }

  public void setCreated(Date created) {
    this.created = created;
  }

  public void setCreatedBy(int createdBy) {
    this.createdBy = createdBy;
  }

  public void setId(int id) {
    this.id = id;
  }

  public void setInitialBalance(BigDecimal initialBalance) {
    this.initialBalance = initialBalance;
  }

  public void setInitialBalanceDate(Date initialBalanceDate) {
    this.initialBalanceDate = initialBalanceDate;
  }

  public void setOwner(String owner) {
    this.owner = owner;
  }

  public void setUpdated(Date updated) {
    this.updated = updated;
  }

  public void setUpdatedBy(int updatedBy) {
    this.updatedBy = updatedBy;
  }

}
