package com.appmoney.model;

import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

  void create(String email);

  boolean exists(String email);

}