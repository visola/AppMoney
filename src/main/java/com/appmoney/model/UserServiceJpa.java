package com.appmoney.model;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.appmoney.repository.UserRepository;

@Service
public class UserServiceJpa implements UserService {

  private final UserRepository userRepository;

  @Autowired
  public UserServiceJpa(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user = userRepository.findByUsername(username);
    if (user == null) {
      throw new UsernameNotFoundException("User does not exist: " + username);
    }
    return user;
  }

  @Override
  @Transactional
  public User create(String username) {
    User user = new User();
    user.setUsername(username);
    return userRepository.save(user);
  }

  @Override
  public boolean exists(String username) {
    return userRepository.existsByUsername(username);
  }

  @Override
  public Optional<User> maybeFindByUsername(String username) {
    return Optional.ofNullable(userRepository.findByUsername(username));
  }

}
