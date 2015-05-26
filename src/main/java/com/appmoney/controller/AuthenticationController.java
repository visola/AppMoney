package com.appmoney.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.appmoney.security.AuthenticationRequest;
import com.appmoney.security.AuthenticationResponse;
import com.appmoney.security.TokenService;

@RequestMapping("/api/v1")
@RestController
public class AuthenticationController {

  @Autowired
  AuthenticationManager authenticationManager;

  @Autowired
  TokenService tokenService;

  @RequestMapping(method=RequestMethod.POST, value="/authenticate")
  public AuthenticationResponse authenticate(@RequestBody AuthenticationRequest authentication) {
    AuthenticationResponse response = tokenService.generateToken(
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(authentication.getUsername(), authentication.getPassword())));
    return response;
  }
}
