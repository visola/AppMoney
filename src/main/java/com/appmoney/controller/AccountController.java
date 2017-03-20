package com.appmoney.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.appmoney.model.Account;
import com.appmoney.model.Permission;
import com.appmoney.model.ResourceNotFoundException;
import com.appmoney.model.Transaction;
import com.appmoney.model.User;
import com.appmoney.model.UserAccountPermission;
import com.appmoney.repository.AccountRepository;
import com.appmoney.repository.TransactionRepository;
import com.appmoney.repository.UserAccountPermissionRepository;

@RestController
@RequestMapping("/api/v1/accounts")
public class AccountController {

  @Autowired
  private AccountRepository accountRepository;

  @Autowired
  private TransactionRepository transactionRepository;

  @Autowired
  private UserAccountPermissionRepository accountUserPermissionRepository;

  @RequestMapping(method=RequestMethod.POST)
  @Transactional
  public Account createAccount(@RequestBody @Valid Account account, @AuthenticationPrincipal User user){
    // Make sure we fill basic data with something that makes sense
    account.setCreated(Calendar.getInstance());
    account.setCreatedBy(user);
    account.setUpdated(Calendar.getInstance());
    account.setUpdatedBy(user);

    // Set initial balance date to now
    if (account.getInitialBalanceDate() == null) {
      account.setInitialBalanceDate(Calendar.getInstance());
    }

    account = accountRepository.save(account);
    accountUserPermissionRepository.save(creatorPermissions(account, user));
    return account;
  }

  @RequestMapping(method = RequestMethod.PUT, value="/{id}")
  public Account updateAccount(@PathVariable int id, @RequestBody @Valid Account account, @AuthenticationPrincipal User user) {
    Account loadedAccount = loadAccountAndCheckOwner(id, user.getId());

    // Make sure we keep things consistent
    account.setId(id);

    // Update update dates and by
    account.setUpdated(Calendar.getInstance());
    account.setUpdatedBy(user);

    // Keep creation date and by
    account.setCreated(loadedAccount.getCreated());
    account.setCreatedBy(loadedAccount.getCreatedBy());

    return accountRepository.save(account);
  }

  @RequestMapping(method = RequestMethod.DELETE, value="/{id}")
  public Account deleteAccount(@PathVariable int id, @AuthenticationPrincipal User user) {
    Account loadedAccount = loadAccountAndCheckOwner(id, user.getId());
    accountRepository.delete(id);
    return loadedAccount;
  }

  @RequestMapping(method = RequestMethod.GET)
  public List<Account> getAccounts(@AuthenticationPrincipal User user) {
    return accountUserPermissionRepository.findByUserId(user.getId()).stream()
        .map(UserAccountPermission::getAccount)
        .filter(a -> a.getDeleted() == null)
        .distinct()
        .collect(Collectors.toList());
  }

  @RequestMapping(method=RequestMethod.GET, value="/balances")
  public Map<Integer, BigDecimal> getBalances(@AuthenticationPrincipal User user) {
    List<Account> accounts = getAccounts(user);
    Set<Integer> accountIds = accounts.stream().map(Account::getId).collect(Collectors.toSet());
    Calendar minDate = accounts.stream().map(Account::getInitialBalanceDate).reduce(Calendar.getInstance(), (date, min) -> date.before(min) ? date: min);

    List<Transaction> transactions = transactionRepository.findByHappenedBetween(minDate, Calendar.getInstance(), accountIds);

    Map<Integer, BigDecimal> result = new HashMap<Integer, BigDecimal>();
    for (Account a : accounts) {
      BigDecimal balance = a.getInitialBalance();
      for (Transaction t : transactions) {
        if (t.equals(a.getInitialBalanceDate()) || t.getHappened().after(a.getInitialBalanceDate()))
        if (Objects.equals(t.getToAccount().getId(), a.getId())) {
          balance = balance.add(t.getValue());
        } else if (t.getFromAccount() != null && Objects.equals(t.getFromAccount().getId(), a.getId())) {
          balance = balance.subtract(t.getValue());
        }
      }
      result.put(a.getId(), balance);
    }

    return result;
  }

  private List<UserAccountPermission> creatorPermissions(Account account, User user) {
    List<UserAccountPermission> permissions = new ArrayList<>();
    for (Permission p : new Permission [] {Permission.READ, Permission.WRITE, Permission.OWNER}) {
      permissions.add(new UserAccountPermission(account, user, p));
    }
    return permissions;
  }

  private Account loadAccountAndCheckOwner(Integer accountId, Integer userId) {
    // Try to load existing account
    Account maybeLoadedAccount = accountRepository.findOne(accountId);
    if (maybeLoadedAccount == null) {
      throw new ResourceNotFoundException(String.format("Account with ID %d not found.", accountId));
    }

    List<UserAccountPermission> permissions = accountUserPermissionRepository.findByAccountIdAndUserId(accountId, userId);

    // Security check
    if (!permissions.stream().anyMatch(aup -> aup.getPermission() != Permission.OWNER)) {
      throw new AccessDeniedException("You do not own this account.");
    }

    return maybeLoadedAccount;
  }

}
