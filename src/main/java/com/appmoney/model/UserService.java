package com.appmoney.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.appmoney.dao.RoleDao;
import com.appmoney.dao.UserDao;

public class UserService implements UserDetailsService {

  @Autowired
  RoleDao roleDao;

  @Autowired
  UserDao userDao;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user = userDao.findUserByEmail(username);
    user.setAuthorities(userDao.findUserAuthoritiesByEmail(username));
    return user;
  }

  public boolean exists(String email) {
    return userDao.exists(email);
  }

  public void create(String email) {
    userDao.create(email);
    roleDao.addRole(email, "USER");
  }

}
