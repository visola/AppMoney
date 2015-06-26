package com.appmoney.security;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import com.appmoney.model.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class TokenService {

  private static final String EMPTY_PASSWORD = "";

  private static final int TOKEN_EXPIRATION = 8 * 3600 * 1000;

  private Map<String, Authentication> authenticationPerToken = new HashMap<>();
  private Map<String, AuthenticationResponse> responsePerToken = new HashMap<>();

  @Autowired
  AuthenticationManager authenticationManager;

  @Autowired
  ObjectMapper objectMapper;

  @Value("${secret}")
  String secret;

  public Optional<Authentication> validateToken(Optional<String> token) {
    if (token.isPresent()) {
      AuthenticationResponse auth = null;

      // Is authentication in memory?
      if (responsePerToken.containsKey(token.get())) {
        auth = responsePerToken.get(token.get());

      } else { // If not, try to recreate authentication from token
        auth = recreateAuthentication(token.get());
      }

      if (auth != null && auth.getExpires() >= System.currentTimeMillis()) {
        return Optional.of(authenticationPerToken.get(token.get()));
      }
    }
    return Optional.empty();
  }

  public AuthenticationResponse generateToken(String email) {
    return generateToken(email, EMPTY_PASSWORD);
  }

  public AuthenticationResponse generateToken(String username, String password) {
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

    // Store authorities as an array of Strings
    data.put("auth", authentication.getAuthorities()
        .stream()
        .map(GrantedAuthority::getAuthority)
        .collect(Collectors.toList()));

    try {
      String json = objectMapper.writeValueAsString(data);
      String signature = DigestUtils.sha256Hex(String.format("%s%s", secret, json));
      String token = String.format("%s-%s", Base64.encodeBase64String(json.getBytes()), Base64.encodeBase64String(signature.getBytes()));

      return storeAuthentication(authentication, expiration, token);
    } catch (JsonProcessingException e) {
      throw new RuntimeException("Error while generating JSON for token.", e);
    }
  }

  private AuthenticationResponse recreateAuthentication(String token) {
    if (token.indexOf("-") >= 0) {
      String dataPart = token.split("-")[0];
      try {
        JsonNode data = objectMapper.readTree(Base64.decodeBase64(dataPart));

        String email = data.get("usr").asText();
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, EMPTY_PASSWORD));

        long expiration = data.get("exp").asLong();

        return storeAuthentication(authentication, expiration, token);
      } catch (IOException e) {
        throw new RuntimeException("Invalid authentication token.");
      }
    }
    throw new RuntimeException("Invalid authentication token.");
  }

  private AuthenticationResponse storeAuthentication(Authentication authentication, long expiration, String token) {
    AuthenticationResponse response = new AuthenticationResponse(token, expiration);

    responsePerToken.put(token, response);
    authenticationPerToken.put(token, authentication);
    return response;
  }

}
