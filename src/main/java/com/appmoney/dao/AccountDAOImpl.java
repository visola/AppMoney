package com.appmoney.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import com.appmoney.account.Account;

public class AccountDAOImpl implements AccountDAO {

  @Autowired
  private NamedParameterJdbcTemplate jdbcTemplate;

  public void insert(Account account) {
    String sql = "INSERT INTO accounts "
        + "(id, owner, initial_balance, initial_balance_date, balance, created, created_by, updated, updated_by) VALUES "
        + "(:id, :owner, :initialBalance, :initialBalanceDate, :balance, :created, :createdBy, :updated, :updatedBy)";

    jdbcTemplate.update(sql, new BeanPropertySqlParameterSource(account));
  }

  public void update(Account account) {
    String sql = "UPDATE accounts SET "
        + "(initial_balance, initial_balance_date, balance, updated, updated_by) = "
        + "(:initialBalance, :initialBalanceDate, :balance, :updated, :updatedBy) "
        + "WHERE id = :id";

    jdbcTemplate.update(sql, new BeanPropertySqlParameterSource(account));
  }

  public void deleteById(int id) {
    String sql = "DELETE FROM accounts WHERE"
        + "id = :id";

    jdbcTemplate.update(sql, new MapSqlParameterSource("id", id));
  }

  public List<Account> selectByOwner(int owner) {
    String sql = "SELECT * FROM accounts WHERE "
        + "owner = :owner";

    return jdbcTemplate.queryForList(sql, new MapSqlParameterSource("owner" , owner), Account.class);
  }
}
