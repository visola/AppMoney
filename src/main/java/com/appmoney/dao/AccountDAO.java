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

import com.appmoney.model.Account;

public class AccountDAO {

  @Autowired
  private NamedParameterJdbcTemplate jdbcTemplate;

  public Account insert(Account account) {
    KeyHolder keyHolder = new GeneratedKeyHolder();
    String sql = "INSERT INTO accounts "
        + "(name, owner, initial_balance, initial_balance_date, balance, created, created_by, updated, updated_by, type) VALUES "
        + "(:name, :owner, :initialBalance, :initialBalanceDate, :balance, :created, :createdBy, :updated, :updatedBy, :type)";

    jdbcTemplate.update(sql, getParameterSource(account), keyHolder);
    account.setId((int) keyHolder.getKeys().get("id"));
    return account;
  }

  public Account update(Account account) {
    String sql = "UPDATE accounts SET "
        + "(name, initial_balance, initial_balance_date, balance, updated, updated_by, \"type\") = "
        + "(:name, :initialBalance, :initialBalanceDate, :balance, :updated, :updatedBy, :type) "
        + "WHERE id = :id";

    jdbcTemplate.update(sql, getParameterSource(account));
    return account;
  }

  public void deleteById(int id) {
    String sql = "DELETE FROM accounts WHERE "
        + "id = :id";

    jdbcTemplate.update(sql, new MapSqlParameterSource("id", id));
  }

  public List<Account> selectByOwner(int owner) {
    String sql = "SELECT * FROM accounts WHERE "
        + "owner = :owner";

    return jdbcTemplate.query(sql, new MapSqlParameterSource("owner" , owner), new BeanPropertyRowMapper<>(Account.class));
  }

  public Optional<Account> findById(Integer id) {
    try {
      return Optional.of(jdbcTemplate.queryForObject(
          "SELECT * FROM accounts WHERE id = :id",
          new MapSqlParameterSource("id", id),
          new BeanPropertyRowMapper<>(Account.class)));
    } catch (EmptyResultDataAccessException e) {
      return Optional.empty();
    }
  }

  private BeanPropertySqlParameterSource getParameterSource(Account account) {
    BeanPropertySqlParameterSource accountParameterSource = new BeanPropertySqlParameterSource(account);
    accountParameterSource.registerSqlType("type", Types.VARCHAR);
    return accountParameterSource;
  }

}
