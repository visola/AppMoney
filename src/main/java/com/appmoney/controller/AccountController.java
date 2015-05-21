package com.appmoney.controller;

import org.springframework.beans.factory.annotation.Autowired;
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

}
