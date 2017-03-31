package com.appmoney.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class UserAccountPermission {

  @GeneratedValue(strategy=GenerationType.IDENTITY)
  @Id
  private Integer id;
  @ManyToOne
  private User user;
  @ManyToOne
  private Account account;
  @Enumerated(EnumType.STRING)
  private Permission permission;

  public UserAccountPermission() {
  }

  public UserAccountPermission(Account account, User user, Permission permission) {
    super();
    this.user = user;
    this.account = account;
    this.permission = permission;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public Account getAccount() {
    return account;
  }

  public void setAccount(Account account) {
    this.account = account;
  }

  public Permission getPermission() {
    return permission;
  }

  public void setPermission(Permission permission) {
    this.permission = permission;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

}
