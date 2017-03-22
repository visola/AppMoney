package com.appmoney.controller;

import java.util.Arrays;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.appmoney.model.Account;
import com.appmoney.model.UserAccountPermission;
import com.appmoney.model.Permission;
import com.appmoney.model.Transaction;
import com.appmoney.model.User;
import com.appmoney.repository.UserAccountPermissionRepository;
import com.appmoney.repository.TransactionRepository;

@RestController
@RequestMapping("/api/v1/transactions")
public class TransactionController {

  @Autowired
  UserAccountPermissionRepository permissionsRepository;

  @Autowired
  TransactionRepository transactionRepository;

  @RequestMapping(method=RequestMethod.POST)
  public Transaction createTransaction(@RequestBody @Valid Transaction transaction, @AuthenticationPrincipal User user) {
    checkPermissions(transaction, user, Permission.OWNER);

    transaction.setCreated(Calendar.getInstance());
    transaction.setCreatedBy(user);

    transaction.setUpdated(Calendar.getInstance());
    transaction.setUpdatedBy(user);

    transactionRepository.save(transaction);
    return transaction;
  }

  @RequestMapping(method=RequestMethod.DELETE, value="/{transactionId}")
  public Transaction deletetransction(@PathVariable Integer transactionId, @AuthenticationPrincipal User user) {
    Optional<Transaction> maybeLoaded = transactionRepository.findById(transactionId);
    if (maybeLoaded.isPresent()) {
      checkPermissions(maybeLoaded.get(), user, Permission.WRITE, Permission.OWNER);
      transactionRepository.delete(transactionId);
      return maybeLoaded.get();
    }
    return null;
  }

  @RequestMapping(method=RequestMethod.PUT, value="/{transactionId}")
  public Transaction updateTransaction(@PathVariable Integer transactionId, @RequestBody @Valid Transaction transaction, @AuthenticationPrincipal User user) {
    if (!Objects.equals(transactionId, transaction.getId())) {
      throw new RuntimeException("Transaction ID doesn't match the one in the path.");
    }
    Optional<Transaction> maybeLoaded = transactionRepository.findById(transactionId);
    if (maybeLoaded.isPresent()) {
      checkPermissions(maybeLoaded.get(), user, Permission.WRITE, Permission.OWNER);

      Transaction loadedTransaction = maybeLoaded.get();

      transaction.setCreated(loadedTransaction.getCreated());
      transaction.setCreatedBy(loadedTransaction.getCreatedBy());

      transaction.setUpdated(Calendar.getInstance());
      transaction.setUpdatedBy(user);

      transactionRepository.save(transaction);
      return transaction;
    }
    return null;
  }

  @RequestMapping(method=RequestMethod.GET, value="/betweenDates")
  public List<Transaction> getTransactionsBetweenDates(
      @RequestParam(required=false) long start,
      @RequestParam(required=false) long end,
      @AuthenticationPrincipal User user) {

    Calendar startCalendar = Calendar.getInstance();
    startCalendar.setTimeInMillis(start);

    Calendar endCalendar = Calendar.getInstance();
    endCalendar.setTimeInMillis(end);

    return transactionRepository.findByHappenedBetween(startCalendar, endCalendar, getVisibleAccountIds(user.getId()));
  }

  @RequestMapping(method=RequestMethod.GET)
  public Page<Transaction> getRecentTransactions(
      @RequestParam(required=false, defaultValue="0") int page,
      @RequestParam(required=false, defaultValue="10") int size,
      @AuthenticationPrincipal User user) {
    PageRequest pageRequest = new PageRequest(page, size);
    return transactionRepository.getRecentTransactions(getVisibleAccountIds(user.getId()), pageRequest);
  }

  @RequestMapping(value="/{transactionId}", method=RequestMethod.GET)
  public Transaction findTransaction(@PathVariable Integer transactionId, @AuthenticationPrincipal User user) {
    Optional<Transaction> transaction = transactionRepository.findById(transactionId);
    if (transaction.isPresent()) {
      checkAnyPermission(transaction.get(), user);
      return transaction.get();
    }
    return null;
  }

  private Set<Integer> getVisibleAccountIds(Integer userId) {
    return permissionsRepository.findByUserId(userId).stream()
        .map(UserAccountPermission::getAccount)
        .filter(a -> a.getDeleted() == null)
        .map(Account::getId)
        .collect(Collectors.toSet());
  }

  private void checkPermissions(Account account, User user, Permission... permissions) {
    if (account == null || account.getId() == null) {
      return;
    }

    Set<Permission> permissionSet = new HashSet<>(Arrays.asList(permissions));
    List<UserAccountPermission> toAccountPermissions = permissionsRepository.findByAccountIdAndUserId(account.getId(), user.getId());
    if (!toAccountPermissions.stream().anyMatch(p -> permissionSet.contains(p.getPermission()))) {
      throw new AccessDeniedException("You don't have permission to add transactions to this account.");
    }
  }

  private void checkPermissions(Transaction transaction, User user, Permission... permissions) {
    checkPermissions(transaction.getToAccount(), user, permissions);
    checkPermissions(transaction.getFromAccount(), user, permissions);
  }

  private void checkAnyPermission(Transaction transaction, User user) {
    checkPermissions(transaction, user, Permission.values());
  }

}
