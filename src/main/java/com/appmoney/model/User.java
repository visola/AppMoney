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

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="users")
public class User implements UserDetails {

  private static final long serialVersionUID = 1L;

  @GeneratedValue(strategy=GenerationType.IDENTITY)
  @Id
  private Integer id;
  private String username;

  @JsonIgnore
  private String password = "";
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

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((id == null) ? 0 : id.hashCode());
    result = prime * result + ((username == null) ? 0 : username.hashCode());
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
    User other = (User) obj;
    if (id == null) {
      if (other.id != null)
        return false;
    } else if (!id.equals(other.id))
      return false;
    if (username == null) {
      if (other.username != null)
        return false;
    } else if (!username.equals(other.username))
      return false;
    return true;
  }

}
