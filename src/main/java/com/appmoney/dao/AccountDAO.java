package com.appmoney.dao;

import java.util.List;

import com.appmoney.account.Account;

public interface AccountDAO {
  
  public void insert(Account account);
  public void update(Account account);
  public void deleteById(int id);
  public List<Account> selectByOwner(int owner);

}
