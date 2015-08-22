package com.appmoney.dao;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import com.appmoney.model.User;

@Component
public class UserDao {

  @Autowired
  NamedParameterJdbcTemplate jdbcTemplate;

  public Optional<User> findUserByEmail(String email) {
    try {
      return Optional.of(jdbcTemplate.queryForObject(
          "select id, email as username from users where email = :email",
          new MapSqlParameterSource("email", email),
          new BeanPropertyRowMapper<>(User.class)));
    } catch (EmptyResultDataAccessException e) {
      return Optional.empty();
    }
  }

  public Collection<? extends GrantedAuthority> findUserAuthoritiesByEmail(String email) {
    List<String> roles = jdbcTemplate.queryForList(
        "select 'ROLE_'||role from roles r join users u on u.id = r.user_id where u.email = :email",
        new MapSqlParameterSource("email", email),
        String.class);
    return roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
  }

  public boolean exists(String email) {
    return jdbcTemplate.queryForObject("select exists (select 1 from users where email = :email)",
        new MapSqlParameterSource("email", email),
        Boolean.class);
  }

  public User create(String email) {
    jdbcTemplate.update("insert into users (email) values (:email)", new MapSqlParameterSource("email", email));
    return findUserByEmail(email).get();
  }

}
