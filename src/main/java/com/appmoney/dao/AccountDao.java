package com.appmoney.dao;

import java.sql.Types;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.appmoney.model.Account;
import com.appmoney.model.Permission;

@Component
public class AccountDao {

  private static final String SELECT_PERMISSIONS_BY_USER_ID_AND_ACCOUNT_ID = "SELECT permission FROM permissions WHERE user_id = :userId AND account_id = :accountId";
  @Autowired
  private NamedParameterJdbcTemplate jdbcTemplate;

  @Transactional
  public Account insert(Account account) {
    KeyHolder keyHolder = new GeneratedKeyHolder();
    String sql = "INSERT INTO accounts "
        + "(name, initial_balance, initial_balance_date, created, created_by, updated, updated_by, type) VALUES "
        + "(:name, :initialBalance, :initialBalanceDate, :created, :createdBy, :updated, :updatedBy, :type)";

    jdbcTemplate.update(sql, getParameterSource(account), keyHolder);
    account.setId((int) keyHolder.getKeys().get("id"));

    insertPermissions(account);

    return account;
  }

  public Account update(Account account) {
    String sql = "UPDATE accounts SET "
        + "(name, initial_balance, initial_balance_date, updated, updated_by, \"type\") = "
        + "(:name, :initialBalance, :initialBalanceDate, :updated, :updatedBy, :type) "
        + "WHERE id = :id";

    jdbcTemplate.update(sql, getParameterSource(account));

    jdbcTemplate.update("DELETE FROM permissions WHERE account_id = :accountId", new MapSqlParameterSource("accountId", account.getId()));
    insertPermissions(account);

    return account;
  }

  @Transactional
  public void deleteById(int id) {
    MapSqlParameterSource paramSource = new MapSqlParameterSource("accountId", id);

    String deletePermissions = "DELETE FROM permissions WHERE account_id = :accountId";
    jdbcTemplate.update(deletePermissions, paramSource);

    String deleteAccount = "DELETE FROM accounts WHERE "
        + "id = :accountId";
    jdbcTemplate.update(deleteAccount, paramSource);
  }

  public List<Account> getVisible(int userId) {
    String sql = "SELECT a.*, (a.initial_balance + ("
        + "   SELECT COALESCE(SUM(CASE WHEN a.id = t.to_account_id THEN value WHEN a.id = from_account_id THEN -value END), 0) "
        + "   FROM transactions t"
        + "   WHERE t.to_account_id = a.id OR t.from_account_id = a.id"
        + "   AND t.happened BETWEEN a.initial_balance_date AND CURRENT_DATE)) AS balance"
        + " FROM accounts a"
        + " WHERE EXISTS (SELECT 1 FROM permissions WHERE user_id = :userId AND account_id = a.id)"
        + " ORDER BY a.name";

    MapSqlParameterSource paramMap = new MapSqlParameterSource("userId" , userId);

    List<Account> accounts = jdbcTemplate.query(sql, paramMap, new BeanPropertyRowMapper<>(Account.class));
    for (Account account : accounts) {
      paramMap.addValue("accountId", account.getId());
      account.setPermissions(jdbcTemplate.query(SELECT_PERMISSIONS_BY_USER_ID_AND_ACCOUNT_ID, paramMap, new PermissionRowMapper()));
    }
    return accounts;
  }

  public Optional<Account> findById(Integer accountId, Integer userId) {
    try {
      Account account = jdbcTemplate.queryForObject(
          "SELECT * FROM accounts WHERE id = :id",
          new MapSqlParameterSource("id", accountId),
          new BeanPropertyRowMapper<>(Account.class));

      MapSqlParameterSource paramMap = new MapSqlParameterSource("userId" , userId);
      paramMap.addValue("accountId", accountId);
      account.setPermissions(jdbcTemplate.query(SELECT_PERMISSIONS_BY_USER_ID_AND_ACCOUNT_ID, paramMap, new PermissionRowMapper()));

      return Optional.of(account);
    } catch (EmptyResultDataAccessException e) {
      return Optional.empty();
    }
  }

  private BeanPropertySqlParameterSource getParameterSource(Account account) {
    BeanPropertySqlParameterSource accountParameterSource = new BeanPropertySqlParameterSource(account);
    accountParameterSource.registerSqlType("type", Types.VARCHAR);
    return accountParameterSource;
  }

  private void insertPermissions(Account account) {
    MapSqlParameterSource paramMap = new MapSqlParameterSource("userId", account.getUpdatedBy());
    paramMap.addValue("accountId", account.getId());
    for (Permission permission : account.getPermissions()) {
      paramMap.addValue("permission", permission.toString());
      jdbcTemplate.update("INSERT INTO permissions (user_id, account_id, permission) VALUES (:userId, :accountId, :permission)", paramMap);
    }
  }

}
