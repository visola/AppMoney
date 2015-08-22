package com.appmoney.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.appmoney.dao.AccountDao;
import com.appmoney.dao.PermissionDao;
import com.appmoney.dao.UserDao;
import com.appmoney.model.Account;
import com.appmoney.model.Permission;
import com.appmoney.model.User;
import com.appmoney.model.UserPermission;
import com.appmoney.model.UserPermissions;

@RestController
@RequestMapping("/api/v1/accounts/{accountId}/permissions")
public class PermissionController {

  @Autowired
  AccountDao accountDao;

  @Autowired
  PermissionDao permissionDao;

  @Autowired
  UserDao userDao;

  @RequestMapping(method=RequestMethod.GET)
  public Collection<UserPermissions> getPermissions(@PathVariable int accountId, @AuthenticationPrincipal User user) {
    Optional<Account> account = accountDao.findById(accountId, user.getId());
    if (account.isPresent()) {
      if (account.get().getPermissions().contains(Permission.OWNER)) {
        return permissionDao.findByAccountId(accountId);
      }
    }
    return new ArrayList<>();
  }

  @RequestMapping(method=RequestMethod.PUT)
  @Transactional
  public Collection<UserPermissions> updatePermissions(@PathVariable int accountId, @RequestBody List<UserPermissions> permissions, @AuthenticationPrincipal User loggedInUser) {
    if (permissions.stream().anyMatch(p -> p.getAccountId() != accountId)) {
      throw new RuntimeException("All permissions have to be for the same account.");
    }

    Optional<Account> account = accountDao.findById(accountId, loggedInUser.getId());
    if (account.isPresent()) {
      if (account.get().getPermissions().contains(Permission.OWNER)) {
        permissionDao.deleteAllPermissions(accountId);
        for (UserPermissions userPermissions : permissions) {
          User user = ensureUser(userPermissions.getEmail());
          for (Permission p : userPermissions.getPermissions()) {
            UserPermission permission = new UserPermission();
            permission.setAccountId(accountId);
            permission.setUserId(user.getId());
            permission.setEmail(user.getUsername());
            permission.setPermission(p);
            permissionDao.save(permission);
          }
        }
      }
    }
    return getPermissions(accountId, loggedInUser);
  }

  private User ensureUser(String email) {
    Optional<User> u = userDao.findUserByEmail(email);
    if (!u.isPresent()) {
      u = Optional.of(userDao.create(email));
    }
    return u.get();
  }

}
