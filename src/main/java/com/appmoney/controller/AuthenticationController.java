package com.appmoney.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.appmoney.security.AppMoneyAuthentication;

@RestController
public class AuthenticationController {

  @Autowired
  AuthenticationManager authenticationManager;

  @RequestMapping(method=RequestMethod.POST)
  public Authentication authenticate(@RequestBody AppMoneyAuthentication authentication) {
    return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authentication.getUsername(), authentication.getPassword()));
  }

}
