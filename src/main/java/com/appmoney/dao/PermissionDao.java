package com.appmoney.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import com.appmoney.model.AccountPermission;

@Component
public class PermissionDao {

  @Autowired
  private NamedParameterJdbcTemplate jdbcTemplate;

  public List<AccountPermission> findByAccountId(int accountId) {
    String selectPermissions = "SELECT u.id AS userId, u.email AS email, p.account_id AS accountId, p.permission AS permission"
        + " FROM permissions p"
        + " JOIN users u ON u.id = p.user_id"
        + " WHERE p.account_id = :accountId";

    return jdbcTemplate.query(selectPermissions, new MapSqlParameterSource("accountId", accountId), new BeanPropertyRowMapper<>(AccountPermission.class));
  }

}
