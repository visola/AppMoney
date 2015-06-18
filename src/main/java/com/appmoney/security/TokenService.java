package com.appmoney.security;

import java.nio.file.AccessDeniedException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;

import org.apache.commons.codec.EncoderException;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.appmoney.model.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class TokenService {

  private static final int TOKEN_EXPIRATION = 10 * 1000;

  private Map<String, Authentication> authenticationPerToken = new HashMap<>();
  private Map<String, AuthenticationResponse> responsePerToken = new HashMap<>();

  @Autowired
  AuthenticationManager authenticationManager;

  @Autowired
  ObjectMapper objectMapper;

  @Value("${secret}")
  String secret;

  public Optional<Authentication> validateToken(Optional<String> token) throws AccessDeniedException {
    if (token.isPresent()) {
      if (responsePerToken.containsKey(token.get())) {
        AuthenticationResponse auth = responsePerToken.get(token.get());
        if (auth.getExpires() >= System.currentTimeMillis()) {
          return Optional.of(authenticationPerToken.get(token.get()));
        } else {
          throw new AccessDeniedException("Authentication expired.");
        }
      }
    }
    return Optional.empty();
  }

  public AuthenticationResponse generateToken(String email) throws JsonProcessingException, EncoderException {
    return generateToken(email, "");
  }

  public AuthenticationResponse generateToken(String username, String password) throws JsonProcessingException, EncoderException {
    Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));

    Optional<Entry<String, Authentication>> entry = authenticationPerToken
        .entrySet().stream().filter(e -> e.getValue().equals(authentication))
        .findFirst();
    if (entry.isPresent()) {
      return responsePerToken.get(entry.get().getKey());
    }

    long expiration = System.currentTimeMillis() + TOKEN_EXPIRATION;

    User user = (User) authentication.getPrincipal();
    Map<String, Object> data = new HashMap<>();
    data.put("exp", expiration);
    data.put("usr", user.getUsername());
    data.put("auth", authentication.getAuthorities());

    String json = objectMapper.writeValueAsString(data);
    String signature = DigestUtils.sha256Hex(String.format("%s%s", secret, json));
    String token = String.format("%s-%s", Base64.encodeBase64String(json.getBytes()), Base64.encodeBase64String(signature.getBytes()));

    AuthenticationResponse response = new AuthenticationResponse();
    response.setToken(token);
    response.setExpires(expiration);

    responsePerToken.put(token, response);
    authenticationPerToken.put(token, authentication);
    return response;
  }

}
