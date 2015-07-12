package com.appmoney.controller;

import java.util.Date;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.appmoney.dao.TransactionDao;
import com.appmoney.model.Transaction;
import com.appmoney.model.User;

@RestController
@RequestMapping("/api/v1/transactions")
public class TransactionController {

  @Autowired
  TransactionDao transactionDao;

  @RequestMapping(method=RequestMethod.POST)
  public Transaction createTransaction(@RequestBody @Valid Transaction transaction, @AuthenticationPrincipal User user) {

    transaction.setCreated(new Date());
    transaction.setCreatedBy(user.getId());

    transaction.setUpdated(new Date());
    transaction.setUpdatedBy(user.getId());

    transactionDao.insertTransaction(transaction);
    return transaction;
  }

}