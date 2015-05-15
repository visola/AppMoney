package com.appmoney.account;

import java.math.BigDecimal;

public class Account {

  private long id;
  private String owner;
  private BigDecimal initialAmount;
  private BigDecimal currentAmount;
  
  protected Account() {}
  
  public Account(String owner, BigDecimal initialAmount, BigDecimal currentAmount){
    this.owner = owner;
    this.initialAmount = initialAmount;
    this.currentAmount = currentAmount;
  }
  
  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getOwner() {
    return owner;
  }

  public void setOwner(String owner) {
    this.owner = owner;
  }

  public BigDecimal getInitialAmount() {
    return initialAmount;
  }

  public void setInitialAmount(BigDecimal initialAmount) {
    this.initialAmount = initialAmount;
  }

  public BigDecimal getCurrentAmount() {
    return currentAmount;
  }

  public void setCurrentAmount(BigDecimal currentAmount) {
    this.currentAmount = currentAmount;
  }

 
  
}
