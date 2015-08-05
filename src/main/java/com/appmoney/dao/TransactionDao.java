package com.appmoney.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
        + "(title, value, happened, from_account_id, to_account_id, category_id, created, created_by, updated, updated_by) VALUES "
        + "(:title, :value, :happened, :fromAccountId, :toAccountId, :categoryId, :created, :createdBy, :updated, :updatedBy)";

    jdbcTemplate.update(sql, new BeanPropertySqlParameterSource(transaction), keyHolder);
    transaction.setId((int) keyHolder.getKeys().get("id"));
    return transaction;
  }

  public Page<Transaction> getRecentTransactions(User user, PageRequest pageRequest) {
    String baseSql = " FROM transactions"
        + " WHERE to_account_id IN ("
        + "   SELECT account_id FROM ownership WHERE user_id = :userId"
        + ")";
    String sortAndLimit = " ORDER BY happened DESC"
        + " LIMIT :pageSize OFFSET :offset";

    MapSqlParameterSource paramSource = new MapSqlParameterSource("userId" , user.getId());
    paramSource.addValue("pageSize", pageRequest.getPageSize());
    paramSource.addValue("offset", pageRequest.getOffset());
    List<Transaction> transactions = jdbcTemplate.query("SELECT *"+baseSql+sortAndLimit, paramSource, new BeanPropertyRowMapper<>(Transaction.class));

    long totalTransactions = jdbcTemplate.queryForObject("SELECT COUNT(1)"+baseSql, paramSource, Long.class);

    return new PageImpl<>(transactions, pageRequest, totalTransactions);
  }

  private void checkAnyPermission(Transaction transaction, Permission... permissions) {
    StringBuilder sql = new StringBuilder("SELECT EXISTS ("
        + "SELECT 1"
        + " FROM ownership"
        + " WHERE user_id = :userId"
        + " AND account_id = :accountId"
        + " AND permission IN (");

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

}
