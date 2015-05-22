package com.appmoney.security;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.UUID;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class TokenService {

  private Map<String, Authentication> authenticationPerToken = new HashMap<>();
  private Map<String, AuthenticationResponse> responsePerToken = new HashMap<>();

  public Optional<Authentication> validateToken(Optional<String> token) {
    if (token.isPresent()) {
      if (responsePerToken.containsKey(token.get())) {
        AuthenticationResponse auth = responsePerToken.get(token.get());
        if (auth.getExpires() >= System.currentTimeMillis()) {
          return Optional.of(authenticationPerToken.get(token.get()));
        }
      }
    }
    return Optional.empty();
  }

  public AuthenticationResponse generateToken(Authentication authentication) {
    Optional<Entry<String, Authentication>> entry = authenticationPerToken.entrySet().stream().filter(e -> e.getValue().equals(authentication)).findFirst();
    if (entry.isPresent()) {
      return responsePerToken.get(entry.get().getKey());
    }    
    
    String token = UUID.randomUUID().toString();
    
    AuthenticationResponse response = new AuthenticationResponse();
    response.setToken(token);
    response.setExpires(System.currentTimeMillis() + 8 * 3600 * 1000);

    responsePerToken.put(token, response);
    authenticationPerToken.put(token, authentication);
    return response;
  }

}
