package com.appmoney.model;

import java.math.BigDecimal;
import java.util.Calendar;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class Transaction {

  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Id
  private Integer id;
  @NotNull
  @Size(min = 2, max = 256)
  private String title;
  @NotNull
  private BigDecimal value;
  @NotNull
  private Calendar happened;
  private Calendar created;
  private Calendar updated;
  private Calendar deleted;
  private String comments;

  @ManyToOne
  private ForecastEntry forecastEntry;

  @ManyToOne
  private User createdBy;

  @ManyToOne
  private User updatedBy;

  @ManyToOne
  private User deletedBy;

  @ManyToOne
  @NotNull
  private Category category;

  @ManyToOne
  @NotNull
  private Account toAccount;

  @ManyToOne
  private Account fromAccount;

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

  public BigDecimal getValue() {
    return value;
  }

  public void setValue(BigDecimal value) {
    this.value = value;
  }

  public Calendar getHappened() {
    return happened;
  }

  public void setHappened(Calendar happened) {
    this.happened = happened;
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

  public Calendar getDeleted() {
    return deleted;
  }

  public void setDeleted(Calendar deleted) {
    this.deleted = deleted;
  }

  public String getComments() {
    return comments;
  }

  public void setComments(String comments) {
    this.comments = comments;
  }

  public ForecastEntry getForecastEntry() {
    return forecastEntry;
  }

  public void setForecastEntry(ForecastEntry forecastEntry) {
    this.forecastEntry = forecastEntry;
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

  public User getDeletedBy() {
    return deletedBy;
  }

  public void setDeletedBy(User deletedBy) {
    this.deletedBy = deletedBy;
  }

  public Category getCategory() {
    return category;
  }

  public void setCategory(Category category) {
    this.category = category;
  }

  public Account getToAccount() {
    return toAccount;
  }

  public void setToAccount(Account toAccount) {
    this.toAccount = toAccount;
  }

  public Account getFromAccount() {
    return fromAccount;
  }

  public void setFromAccount(Account fromAccount) {
    this.fromAccount = fromAccount;
  }

}
