package com.appmoney.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.appmoney.dao.AccountDao;
import com.appmoney.dao.PermissionDao;
import com.appmoney.model.Account;
import com.appmoney.model.AccountPermission;
import com.appmoney.model.Permission;
import com.appmoney.model.User;

@RestController
@RequestMapping("/api/v1/accounts/{accountId}/permissions")
public class PermissionController {

  @Autowired
  AccountDao accountDao;

  @Autowired
  PermissionDao permissionDao;

  @RequestMapping(method=RequestMethod.GET)
  public List<AccountPermission> getPermissions(@PathVariable int accountId, @AuthenticationPrincipal User user) {
    Optional<Account> account = accountDao.findById(accountId, user.getId());
    if (account.isPresent()) {
      if (account.get().getPermissions().contains(Permission.OWNER)) {
        return permissionDao.findByAccountId(accountId);
      }
    }
    return new ArrayList<>();
  }

}
