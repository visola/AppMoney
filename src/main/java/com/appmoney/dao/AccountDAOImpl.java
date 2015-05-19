package com.appmoney.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import com.appmoney.account.Account;

public class AccountDAOImpl implements AccountDAO{

  @Autowired
  private JdbcTemplate jdbcTemplate;

  public void insert(Account account) {
    String sql = "INSERT INTO ACCOUNTS (OWNER, INITIAL_AMOUNT, CURRENT_AMOUNT) VALUES (?, ?, ?)";
   
    jdbcTemplate.update(sql, new Object[] { account.getOwner(), account.getInitialAmount(), account.getCurrentAmount() });
  } 

}
