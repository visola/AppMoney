package com.appmoney.model;

import java.time.LocalDate;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class User implements UserDetails {

  private static final long serialVersionUID = 1L;

  private Integer id;

  private Collection<? extends GrantedAuthority> authorities;
  private String password = "";
  private String username;
  private LocalDate expiresOn;
  private LocalDate lockedOn;
  private LocalDate passwordExpired;
  private LocalDate disabled;

  public Collection<? extends GrantedAuthority> getAuthorities() {
    return authorities;
  }

  public LocalDate getDisabled() {
    return disabled;
  }

  public LocalDate getExpiresOn() {
    return expiresOn;
  }

  public Integer getId() {
    return id;
  }

  public LocalDate getLockedOn() {
    return lockedOn;
  }

  public String getPassword() {
    return password;
  }

  public LocalDate getPasswordExpired() {
    return passwordExpired;
  }

  public String getUsername() {
    return username;
  }

  @Override
  public boolean isAccountNonExpired() {
    return expiresOn == null || expiresOn.isBefore(LocalDate.now());
  }

  @Override
  public boolean isAccountNonLocked() {
    return lockedOn == null || lockedOn.isBefore(LocalDate.now());
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return passwordExpired == null || passwordExpired.isBefore(LocalDate.now());
  }

  @Override
  public boolean isEnabled() {
    return disabled == null || disabled.isAfter(LocalDate.now());
  }

  public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
    this.authorities = authorities;
  }

  public void setDisabled(LocalDate disabled) {
    this.disabled = disabled;
  }

  public void setExpiresOn(LocalDate expiresOn) {
    this.expiresOn = expiresOn;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public void setLockedOn(LocalDate lockedOn) {
    this.lockedOn = lockedOn;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public void setPasswordExpired(LocalDate passwordExpired) {
    this.passwordExpired = passwordExpired;
  }

  public void setUsername(String username) {
    this.username = username;
  }

}
