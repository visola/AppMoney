package com.appmoney.security;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class TokenService {

  private Map<String, Authentication> authenticationPerToken = new HashMap<>();

  public Optional<Authentication> validateToken(Optional<String> token) {
    if (token.isPresent()) {
      return Optional.ofNullable(authenticationPerToken.get(token.get()));
    }
    return Optional.empty();
  }

}
