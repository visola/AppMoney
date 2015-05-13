package com.appmoney.security;

public class AuthenticationResponse {

  private String token;
  private Long expires;

  public Long getExpires() {
    return expires;
  }

  public String getToken() {
    return token;
  }

  public void setExpires(Long expires) {
    this.expires = expires;
  }

  public void setToken(String token) {
    this.token = token;
  }
 
}
