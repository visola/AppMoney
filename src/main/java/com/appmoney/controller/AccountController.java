package com.appmoney.controller;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.appmoney.dao.AccountDao;
import com.appmoney.model.Account;
import com.appmoney.model.Permission;
import com.appmoney.model.ResourceNotFoundException;
import com.appmoney.model.User;

@RestController
@RequestMapping("/api/v1/accounts")
public class AccountController {

  @Autowired
  private AccountDao accountDao;

  @RequestMapping(method=RequestMethod.POST)
  public Account createAccount(@RequestBody @Valid Account account, @AuthenticationPrincipal User user){
    // Make sure we fill basic data with something that makes sense
    account.setCreated(new Date());
    account.setCreatedBy(user.getId());
    account.setUpdated(new Date());
    account.setUpdatedBy(user.getId());

    account.setPermissions(Arrays.asList(Permission.READ, Permission.WRITE, Permission.OWNER));

    // Set initial balance date to now
    if (account.getInitialBalanceDate() == null) {
      account.setInitialBalanceDate(new Date());
    }

    return accountDao.insert(account);
  }

  @RequestMapping(method = RequestMethod.PUT, value="/{id}")
  public Account updateAccount(@PathVariable int id, @RequestBody @Valid Account account, @AuthenticationPrincipal User user) {
    Account loadedAccount = loadAccountAndCheckOwner(id, user.getId());

    // Make sure we keep things consistent
    account.setId(id);
    account.setPermissions(loadedAccount.getPermissions());

    // Update update dates and by
    account.setUpdated(new Date());
    account.setUpdatedBy(user.getId());

    // Keep creation date and by
    account.setCreated(loadedAccount.getCreated());
    account.setCreatedBy(loadedAccount.getCreatedBy());

    return accountDao.update(account);
  }

  @RequestMapping(method = RequestMethod.DELETE, value="/{id}")
  public Account deleteAccount(@PathVariable int id, @AuthenticationPrincipal User user) {
    Account loadedAccount = loadAccountAndCheckOwner(id, user.getId());
    accountDao.deleteById(id);
    return loadedAccount;
  }

  @RequestMapping(method = RequestMethod.GET)
  public List<Account> selectAccounts(@AuthenticationPrincipal User user) {
    return accountDao.getVisible(user.getId());
  }

  private Account loadAccountAndCheckOwner(Integer accountId, Integer userId) {
    // Try to load existing account
    Optional<Account> maybeLoadedAccount = accountDao.findById(accountId, userId);
    if (!maybeLoadedAccount.isPresent()) {
      throw new ResourceNotFoundException(String.format("Account with ID %d not found.", accountId));
    }

    // Security check
    if (maybeLoadedAccount.get().getPermissions().contains(Permission.OWNER)) {
      throw new AccessDeniedException("You do not own this account.");
    }

    return maybeLoadedAccount.get();
  }

}
