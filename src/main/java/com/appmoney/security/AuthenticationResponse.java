package com.appmoney.security;

public class AuthenticationResponse {

  private String token;
  private Long expires;

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    AuthenticationResponse other = (AuthenticationResponse) obj;
    if (expires == null) {
      if (other.expires != null)
        return false;
    } else if (!expires.equals(other.expires))
      return false;
    if (token == null) {
      if (other.token != null)
        return false;
    } else if (!token.equals(other.token))
      return false;
    return true;
  }

  public Long getExpires() {
    return expires;
  }

  public String getToken() {
    return token;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((expires == null) ? 0 : expires.hashCode());
    result = prime * result + ((token == null) ? 0 : token.hashCode());
    return result;
  }

  public void setExpires(Long expires) {
    this.expires = expires;
  }

  public void setToken(String token) {
    this.token = token;
  }

  @Override
  public String toString() {
    return "TokenResponse [token=" + token + ", expires=" + expires + "]";
  }

}
