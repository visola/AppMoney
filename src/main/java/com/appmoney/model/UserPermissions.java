package com.appmoney.model;

import java.util.HashSet;
import java.util.Set;

public class UserPermissions {

  private int accountId;
  private int userId;
  private String email;
  private Set<Permission> permissions = new HashSet<>();

  public UserPermissions(UserPermission accountPermission) {
    this.accountId = accountPermission.getAccountId();
    this.userId = accountPermission.getUserId();
    this.email = accountPermission.getEmail();
    this.permissions.add(accountPermission.getPermission());
  }

  public int getAccountId() {
    return accountId;
  }

  public void setAccountId(int accountId) {
    this.accountId = accountId;
  }

  public int getUserId() {
    return userId;
  }

  public void setUserId(int userId) {
    this.userId = userId;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public Set<Permission> getPermissions() {
    return permissions;
  }

  public void setPermissions(Set<Permission> permissions) {
    this.permissions = permissions;
  }

}
