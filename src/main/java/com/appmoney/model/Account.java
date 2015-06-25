package com.appmoney.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

public class Account {
  
  private Integer id;
  @NotNull
  @Size(min=2, max=250)
  private String name;
  @NotNull
  @Min(1)
  private Integer owner;
  @NotNull
  private BigDecimal initialBalance;
  @NotNull
  @Past
  private Date initialBalanceDate;
  private BigDecimal balance;
  private Date created;
  private Integer createdBy;
  private Date updated;
  private Integer updatedBy;
  @NotNull
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

  public String getName() {
    return name;
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

  public void setName(String name) {
    this.name = name;
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
