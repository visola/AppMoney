package com.appmoney.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import com.appmoney.model.Transaction;

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

}
