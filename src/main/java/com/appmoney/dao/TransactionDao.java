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

import com.appmoney.model.Transaction;
import com.appmoney.model.User;

@Component
public class TransactionDao {

  @Autowired
  private NamedParameterJdbcTemplate jdbcTemplate;

  public Transaction insertTransaction(Transaction transaction) {
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
        + "   SELECT id FROM accounts WHERE owner = :ownerId"
        + ")";
    String sortAndLimit = " ORDER BY happened DESC"
        + " LIMIT :pageSize OFFSET :offset";

    MapSqlParameterSource paramSource = new MapSqlParameterSource("ownerId" , user.getId());
    paramSource.addValue("pageSize", pageRequest.getPageSize());
    paramSource.addValue("offset", pageRequest.getOffset());
    List<Transaction> transactions = jdbcTemplate.query("SELECT *"+baseSql+sortAndLimit, paramSource, new BeanPropertyRowMapper<>(Transaction.class));

    long totalTransactions = jdbcTemplate.queryForObject("SELECT COUNT(1)"+baseSql, paramSource, Long.class);

    return new PageImpl<>(transactions, pageRequest, totalTransactions);
  }

}
