package com.appmoney.model;

import java.math.BigDecimal;
import java.util.Calendar;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

@Entity
public class Account {

  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Id
  private Integer id;
  @NotNull
  @Size(min = 2, max = 250)
  private String name;
  @NotNull
  private BigDecimal initialBalance;
  @NotNull
  @Past
  private Calendar initialBalanceDate;
  private Calendar created;
  @ManyToOne
  private User createdBy;
  private Calendar updated;
  @ManyToOne
  private User updatedBy;
  @NotNull
  @Enumerated(EnumType.STRING)
  private AccountType type;
  private Calendar deleted;
  @ManyToOne
  private User deletedBy;

  public Calendar getCreated() {
    return created;
  }

  public User getCreatedBy() {
    return createdBy;
  }

  public Integer getId() {
    return id;
  }

  public BigDecimal getInitialBalance() {
    return initialBalance;
  }

  public Calendar getInitialBalanceDate() {
    return initialBalanceDate;
  }

  public String getName() {
    return name;
  }

  public Calendar getUpdated() {
    return updated;
  }

  public User getUpdatedBy() {
    return updatedBy;
  }

  public void setCreated(Calendar created) {
    this.created = created;
  }

  public void setCreatedBy(User createdBy) {
    this.createdBy = createdBy;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public void setInitialBalance(BigDecimal initialBalance) {
    this.initialBalance = initialBalance;
  }

  public void setInitialBalanceDate(Calendar initialBalanceDate) {
    this.initialBalanceDate = initialBalanceDate;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setUpdated(Calendar updated) {
    this.updated = updated;
  }

  public void setUpdatedBy(User updatedBy) {
    this.updatedBy = updatedBy;
  }

  public AccountType getType() {
    return type;
  }

  public void setType(AccountType type) {
    this.type = type;
  }

  public User getDeletedBy() {
    return deletedBy;
  }

  public void setDeletedBy(User deletedBy) {
    this.deletedBy = deletedBy;
  }

  public Calendar getDeleted() {
    return deleted;
  }

  public void setDeleted(Calendar deleted) {
    this.deleted = deleted;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((id == null) ? 0 : id.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Account other = (Account) obj;
    if (id == null) {
      if (other.id != null)
        return false;
    } else if (!id.equals(other.id))
      return false;
    return true;
  }

}
