package com.appmoney.controller;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.appmoney.dao.TransactionDao;
import com.appmoney.model.Permission;
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

  @RequestMapping(method=RequestMethod.DELETE, value="/{transactionId}")
  public Transaction deletetransction(@PathVariable Integer transactionId, @AuthenticationPrincipal User user) {
    Optional<Transaction> maybeLoaded = transactionDao.findById(transactionId);
    if (maybeLoaded.isPresent()) {
      transactionDao.checkAnyPermission(maybeLoaded.get(), Permission.WRITE, Permission.OWNER);
      transactionDao.delete(transactionId, user.getId());
      return maybeLoaded.get();
    }
    return null;
  }

  @RequestMapping(method=RequestMethod.PUT, value="/{transactionId}")
  public Transaction updateTransaction(@PathVariable Integer transactionId, @RequestBody @Valid Transaction transaction, @AuthenticationPrincipal User user) {
    if (!Objects.equals(transactionId, transaction.getId())) {
      throw new RuntimeException("Transaction ID doesn't match the one in the path.");
    }
    Optional<Transaction> maybeLoaded = transactionDao.findById(transactionId);
    if (maybeLoaded.isPresent()) {
      transactionDao.checkAnyPermission(maybeLoaded.get(), Permission.WRITE, Permission.OWNER);

      Transaction loadedTransaction = maybeLoaded.get();

      transaction.setCreated(loadedTransaction.getCreated());
      transaction.setCreatedBy(loadedTransaction.getCreatedBy());

      transaction.setUpdated(new Date());
      transaction.setUpdatedBy(user.getId());

      transactionDao.update(transaction);
      return transaction;
    }
    return null;
  }

  @RequestMapping(method=RequestMethod.GET, value="/betweenDates")
  public List<Transaction> getTransactionsBetweenDates(
      @RequestParam(required=false) long start,
      @RequestParam(required=false) long end,
      @AuthenticationPrincipal User user) {
    return transactionDao.findBetween(new Date(start), new Date(end), user);
  }

  @RequestMapping(method=RequestMethod.GET)
  public Page<Transaction> getRecentTransactions(
      @RequestParam(required=false, defaultValue="0") int page,
      @RequestParam(required=false, defaultValue="10") int size,
      @AuthenticationPrincipal User user) {
    PageRequest pageRequest = new PageRequest(page, size);
    return transactionDao.getRecentTransactions(user, pageRequest);
  }

  @RequestMapping(value="/{transactionId}", method=RequestMethod.GET)
  public Transaction findTransaction(@PathVariable Integer transactionId, @AuthenticationPrincipal User user) {
    Optional<Transaction> transaction = transactionDao.findById(transactionId);
    if (transaction.isPresent()) {
      transactionDao.checkAnyPermission(transaction.get(), Permission.values());
      return transaction.get();
    }
    return null;
  }

}
