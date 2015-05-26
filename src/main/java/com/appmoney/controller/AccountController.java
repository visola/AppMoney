package com.appmoney.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.appmoney.dao.AccountDAO;
import com.appmoney.model.Account;

@RestController
@RequestMapping("/api/v1/accounts")
public class AccountController {

  @Autowired
  private AccountDAO accountDAO;

  @RequestMapping(method=RequestMethod.POST)
  public Account createAccount(@RequestBody Account account){
    accountDAO.insert(account);
    return account;
  }

  @RequestMapping(method = RequestMethod.PUT)
  public Account updateAccount(@RequestBody Account account) {
    return accountDAO.update(account);
  }
  
  @RequestMapping(method = RequestMethod.DELETE, value="/{id}")
  public void deleteAccount(@PathVariable int id) {
    accountDAO.deleteById(id);
  }
  
  @RequestMapping(method = RequestMethod.GET)
  public List<Account> selectAccounts(int owner) {
    return accountDAO.selectByOwner(owner);
  }
}
