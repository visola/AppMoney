package com.appmoney.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.appmoney.account.Account;
import com.appmoney.dao.AccountDAO;

@RestController
@RequestMapping("/api/v1/accounts")
public class AccountController {

  @Autowired
  private AccountDAO accountDAO;

  @RequestMapping(method = RequestMethod.POST)
  public Account createAccount(@RequestBody Account account) {
    accountDAO.insert(account);
    return account;
  }

  @RequestMapping(method = RequestMethod.PUT)
  public Account updateAccount(@RequestBody Account account) {
    accountDAO.update(account);
    return null;
  }
  
  @RequestMapping(method = RequestMethod.DELETE)
  public Account deleteAccount(@RequestBody int id) {
    accountDAO.deleteById(id);
    return null;
  }
  
  @RequestMapping(method = RequestMethod.GET)
  public Account selectAccounts(int owner) {
    accountDAO.selectByOwner(owner);
    return null;
  }
}
