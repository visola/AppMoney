package com.appmoney.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class RoleDao {

  @Autowired
  NamedParameterJdbcTemplate jdbcTemplate;

  public void addRole(String email, String role) {
    MapSqlParameterSource paramMap = new MapSqlParameterSource("email", email);
    paramMap.addValue("role", role);
    jdbcTemplate.update("insert into roles (user_id, role) values ((select id from users where email = :email), :role)",paramMap);
  }

}
