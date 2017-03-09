package com.appmoney.model;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

  User create(String username);

  boolean exists(String username);

  Optional<User> maybeFindByUsername(String username);

}