package com.appmoney.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.appmoney.model.UserService;
import com.appmoney.security.AuthenticationResponse;
import com.appmoney.security.TokenService;

@Controller
@Profile("test")
public class TestController {

  @Autowired
  TokenService tokenService;

  @Autowired
  UserService userService;

  public TestController() {
    System.out.println("Test");
  }

  @RequestMapping(value="/testLogin", method=RequestMethod.GET)
  public ModelAndView testLogin(String username) {
    if (!userService.exists(username)) {
      userService.create(username);
    }
    AuthenticationResponse authResponse = tokenService.generateToken(username);

    ModelAndView mv = new ModelAndView("oauth2callback");

    mv.addObject("email", username);
    mv.addObject("expires", authResponse.getExpires());
    mv.addObject("token", authResponse.getToken());
    mv.addObject("path", "/");
    return mv;
  }

}
