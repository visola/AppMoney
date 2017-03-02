package com.appmoney.model;

import java.util.Calendar;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Table(name="users")
public class User implements UserDetails {

  private static final long serialVersionUID = 1L;

  @GeneratedValue(strategy=GenerationType.IDENTITY)
  @Id
  private Integer id;

  private String password = "";
  private String username;
  private Calendar expiresOn;
  private Calendar lockedOn;
  private Calendar passwordExpired;
  private Calendar disabled;

  @ManyToMany(fetch = FetchType.EAGER)
  private List<Authority> authorities;

  public List<Authority> getAuthorities() {
    return authorities;
  }

  public Calendar getDisabled() {
    return disabled;
  }

  public Calendar getExpiresOn() {
    return expiresOn;
  }

  public Integer getId() {
    return id;
  }

  public Calendar getLockedOn() {
    return lockedOn;
  }

  public String getPassword() {
    return password;
  }

  public Calendar getPasswordExpired() {
    return passwordExpired;
  }

  public String getUsername() {
    return username;
  }

  @Override
  public boolean isAccountNonExpired() {
    return expiresOn == null || expiresOn.before(Calendar.getInstance());
  }

  @Override
  public boolean isAccountNonLocked() {
    return lockedOn == null || lockedOn.before(Calendar.getInstance());
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return passwordExpired == null || passwordExpired.before(Calendar.getInstance());
  }

  @Override
  public boolean isEnabled() {
    return disabled == null || disabled.after(Calendar.getInstance());
  }

  public void setAuthorities(List<Authority> authorities) {
    this.authorities = authorities;
  }

  public void setDisabled(Calendar disabled) {
    this.disabled = disabled;
  }

  public void setExpiresOn(Calendar expiresOn) {
    this.expiresOn = expiresOn;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public void setLockedOn(Calendar lockedOn) {
    this.lockedOn = lockedOn;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public void setPasswordExpired(Calendar passwordExpired) {
    this.passwordExpired = passwordExpired;
  }

  public void setUsername(String username) {
    this.username = username;
  }

}
