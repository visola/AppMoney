package com.appmoney.controller;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.appmoney.model.UserAccountPermission;
import com.appmoney.model.Permission;
import com.appmoney.model.User;
import com.appmoney.model.UserService;
import com.appmoney.repository.AccountRepository;
import com.appmoney.repository.UserAccountPermissionRepository;

@RestController
@RequestMapping("/api/v1/accounts")
public class PermissionController {

  @Autowired
  AccountRepository accountRepository;

  @Autowired
  UserAccountPermissionRepository permissionRepository;

  @Autowired
  UserService userService;

  @RequestMapping(method=RequestMethod.GET, value="/permissions")
  public Collection<UserAccountPermission> getPermissions(@AuthenticationPrincipal User user) {
    return permissionRepository.findByUserId(user.getId());
  }

  @RequestMapping(method=RequestMethod.GET, value="/{accountId}/permissions")
  public Collection<UserAccountPermission> getPermissions(@PathVariable int accountId, @AuthenticationPrincipal User user) {
    return permissionRepository.findByAccountId(accountId);
  }

  @RequestMapping(method=RequestMethod.PUT, value="/{accountId}/permissions")
  @Transactional
  public Collection<UserAccountPermission> updatePermissions(
      @PathVariable int accountId,
      @RequestBody List<UserAccountPermission> permissions,
      @AuthenticationPrincipal User user) {

    if (permissions.stream().anyMatch(p -> p.getAccount().getId() != accountId)) {
      throw new RuntimeException("All permissions have to be for the same account.");
    }

    checkPermission(accountId, user.getId());

    permissionRepository.deleteByAccountId(accountId);
    permissions.stream().forEach(p -> {
      if (p.getUser().getId() == null) {
        User u = ensureUser(p.getUser().getUsername());
        p.setUser(u);
      }
      p.setId(null);
      permissionRepository.save(p);
    });

    return permissionRepository.findByAccountId(accountId);
  }

  private void checkPermission(Integer accountId, Integer userId) {
    List<UserAccountPermission> loadedPermissions = permissionRepository.findByAccountIdAndUserId(accountId, userId);
    if (!loadedPermissions.stream().anyMatch(p -> p.getPermission() == Permission.OWNER)) {
      throw new AccessDeniedException("You do not own this account.");
    }
  }

  private User ensureUser(String email) {
    Optional<User> u = userService.maybeFindByUsername(email);
    if (!u.isPresent()) {
      u = Optional.of(userService.create(email));
    }
    return u.get();
  }

}
