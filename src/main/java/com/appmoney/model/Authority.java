package com.appmoney.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.security.core.GrantedAuthority;

@Entity
public class Authority implements GrantedAuthority {

  private static final long serialVersionUID = 1L;

  public enum Role {
    ADMIN("ROLE_ADMIN"),
    ;

    private String name;

    Role(String name) {
      this.name = name;
    }

    public String getName() {
      return name;
    }
  }

  public Authority() {
  }

  public Authority(String authority) {
    this.authority = authority;
  }

  @GeneratedValue(strategy=GenerationType.IDENTITY)
  @Id
  private Integer id;
  private String authority;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  @Override
  public String getAuthority() {
    return authority;
  }

  public void setAuthority(String authority) {
    this.authority = authority;
  }

}
