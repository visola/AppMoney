package com.appmoney.dao;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import com.appmoney.model.Permission;
import com.appmoney.model.Transaction;
import com.appmoney.model.User;

@Component
public class TransactionDao {

  @Autowired
  private NamedParameterJdbcTemplate jdbcTemplate;

  public Transaction insertTransaction(Transaction transaction) {
    checkAnyPermission(transaction, Permission.WRITE, Permission.OWNER);

    KeyHolder keyHolder = new GeneratedKeyHolder();
    String sql = "INSERT INTO transactions "
        + "(title, value, happened, from_account_id, to_account_id, category_id, created, created_by, updated, updated_by, comments, forecast_entry_id) VALUES "
        + "(:title, :value, :happened, :fromAccountId, :toAccountId, :categoryId, :created, :createdBy, :updated, :updatedBy, :comments, :forecastEntryId)";

    jdbcTemplate.update(sql, new BeanPropertySqlParameterSource(transaction), keyHolder);
    transaction.setId((int) keyHolder.getKeys().get("id"));
    return transaction;
  }

  public Page<Transaction> getRecentTransactions(User user, PageRequest pageRequest) {
    String selectAccountIds = "SELECT a.id"
        + " FROM permissions p"
        + " JOIN accounts a ON p.account_id = a.id"
        + " WHERE user_id = :userId"
        + " AND a.deleted IS NULL";

    String baseSql = " FROM transactions"
        + " WHERE ("
        + "    to_account_id IN (" + selectAccountIds + ")"
        + "    OR from_account_id IN (" + selectAccountIds + ")"
        + ") AND deleted IS NULL";

    String sortAndLimit = " ORDER BY happened DESC, id"
        + " LIMIT :pageSize OFFSET :offset";

    MapSqlParameterSource paramSource = new MapSqlParameterSource("userId" , user.getId());
    paramSource.addValue("pageSize", pageRequest.getPageSize());
    paramSource.addValue("offset", pageRequest.getOffset());
    List<Transaction> transactions = jdbcTemplate.query("SELECT *"+baseSql+sortAndLimit, paramSource, new BeanPropertyRowMapper<>(Transaction.class));

    long totalTransactions = jdbcTemplate.queryForObject("SELECT COUNT(1)"+baseSql, paramSource, Long.class);

    return new PageImpl<>(transactions, pageRequest, totalTransactions);
  }

  public Optional<Transaction> findById(Integer transactionId) {
    String sql = "SELECT * FROM transactions WHERE id = :id";
    try {
      return Optional.of(jdbcTemplate.queryForObject(sql, new MapSqlParameterSource("id", transactionId), new BeanPropertyRowMapper<>(Transaction.class)));
    } catch (EmptyResultDataAccessException e) {
      return Optional.empty();
    }
  }

  public void checkAnyPermission(Transaction transaction, Permission... permissions) {
    StringBuilder sql = new StringBuilder("SELECT EXISTS ("
        + "SELECT 1"
        + " FROM permissions p"
        + " JOIN accounts a ON p.account_id = a.id"
        + " WHERE p.user_id = :userId"
        + " AND a.deleted IS NULL"
        + " AND p.account_id = :accountId"
        + " AND p.permission IN (");

    for (Permission permission : permissions) {
      sql.append("'");
      sql.append(permission.toString());
      sql.append("',");
    }
    sql.setLength(sql.length() - 1);
    sql.append("))");

    MapSqlParameterSource paramMap = new MapSqlParameterSource("userId" , transaction.getCreatedBy());
    paramMap.addValue("accountId", transaction.getToAccountId());
    if (!jdbcTemplate.queryForObject(sql.toString(), paramMap, Boolean.class)) {
      throw new RuntimeException("You don't have permission to execute this action");
    }
  }

  public void update(Transaction transaction) {
    String sql = "UPDATE transactions"
        + " SET (title, value, happened, from_account_id, to_account_id, category_id, created, created_by, updated, updated_by, comments, forecast_entry_id)"
        + " = (:title, :value, :happened, :fromAccountId, :toAccountId, :categoryId, :created, :createdBy, :updated, :updatedBy, :comments, :forecastEntryId)"
        + " WHERE id = :id";

    jdbcTemplate.update(sql, new BeanPropertySqlParameterSource(transaction));
  }

  public void delete(Integer transactionId, Integer userId) {
    MapSqlParameterSource paramSource = new MapSqlParameterSource("transactionId", transactionId);
    paramSource.addValue("userId", userId);

    String sql = "UPDATE transactions SET (deleted, deleted_by)"
        + " = (CURRENT_TIMESTAMP, :userId)"
        + " WHERE id = :transactionId";

    jdbcTemplate.update(sql, paramSource);
  }

  public List<Transaction> findBetween(Date start, Date end, User user) {
    String selectAccountIds = "SELECT a.id"
        + " FROM permissions p"
        + " JOIN accounts a ON p.account_id = a.id"
        + " WHERE user_id = :userId"
        + " AND a.deleted IS NULL";

    String sql = " FROM transactions"
        + " WHERE ("
        + "    to_account_id IN (" + selectAccountIds + ")"
        + "    OR from_account_id IN (" + selectAccountIds + ")"
        + ") AND deleted IS NULL"
        + " AND happened BETWEEN :start AND :end";

    MapSqlParameterSource paramSource = new MapSqlParameterSource("userId" , user.getId());
    paramSource.addValue("start", start);
    paramSource.addValue("end", end);
    return jdbcTemplate.query("SELECT *"+sql, paramSource, new BeanPropertyRowMapper<>(Transaction.class));
  }

}
