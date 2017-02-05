package com.appmoney.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class Forecast {

  private Integer id;
  private List<Permission> permissions = new ArrayList<>();
  private Date created;
  private Integer createdBy;
  private Date updated;
  private Integer updatedBy;
  @NotNull @Min(1) @Max(28) private Integer startDayOfMonth = 1;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public List<Permission> getPermissions() {
    return permissions;
  }

  public void setPermissions(List<Permission> permissions) {
    this.permissions = permissions;
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

  public Integer getStartDayOfMonth() {
    return startDayOfMonth;
  }

  public void setStartDayOfMonth(Integer startDayOfMonth) {
    this.startDayOfMonth = startDayOfMonth;
  }

}
