package com.appmoney.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public class GrantedAuthorityRowMapper implements RowMapper<GrantedAuthority> {

  @Override
  public GrantedAuthority mapRow(ResultSet rs, int rowNum) throws SQLException {
    return new SimpleGrantedAuthority(rs.getString("role"));
  }

}
