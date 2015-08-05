package com.appmoney.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.appmoney.model.Permission;

public class PermissionRowMapper implements RowMapper<Permission> {

  @Override
  public Permission mapRow(ResultSet rs, int rowNum) throws SQLException {
    return Permission.valueOf(rs.getString(1));
  }

}
