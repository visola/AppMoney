package com.appmoney.dao;

import java.sql.Types;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import com.appmoney.model.UserPermission;
import com.appmoney.model.UserPermissions;

@Component
public class PermissionDao {

  @Autowired
  private NamedParameterJdbcTemplate jdbcTemplate;

  public void deleteAllPermissions(int accountId) {
    jdbcTemplate.update("DELETE FROM permissions WHERE account_id = :accountId", new MapSqlParameterSource("accountId", accountId));
  }

  public Collection<UserPermissions> findByAccountId(int accountId) {
    String selectPermissions = "SELECT u.id AS userId, u.email AS email, p.account_id AS accountId, p.permission AS permission"
        + " FROM permissions p"
        + " JOIN users u ON u.id = p.user_id"
        + " WHERE p.account_id = :accountId";

    List<UserPermission> allPermissions = jdbcTemplate.query(selectPermissions, new MapSqlParameterSource("accountId", accountId), new BeanPropertyRowMapper<>(UserPermission.class));

    Map<Integer, UserPermissions> result = new HashMap<>();
    allPermissions.forEach(p -> result.computeIfAbsent(p.getUserId(), userId -> new UserPermissions(p)).getPermissions().add(p.getPermission()));
    return result.values();
  }

  public void save(UserPermission permission) {
    String insertPermission = "INSERT INTO permissions (account_id, user_id, permission) VALUES (:accountId, :userId, :permission)";
    jdbcTemplate.update(insertPermission, getParameterSource(permission));
  }

  private BeanPropertySqlParameterSource getParameterSource(UserPermission permission) {
    BeanPropertySqlParameterSource accountParameterSource = new BeanPropertySqlParameterSource(permission);
    accountParameterSource.registerSqlType("permission", Types.VARCHAR);
    return accountParameterSource;
  }

}
