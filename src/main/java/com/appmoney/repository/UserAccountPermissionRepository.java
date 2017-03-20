package com.appmoney.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.appmoney.model.UserAccountPermission;

public interface UserAccountPermissionRepository extends PagingAndSortingRepository<UserAccountPermission, Integer> {

  List<UserAccountPermission> findByAccountId(int accountId);

  List<UserAccountPermission> findByAccountIdAndUserId(Integer accountId, Integer userId);

  List<UserAccountPermission> findByUserId(Integer userId);

  @Modifying
  @Query("delete from UserAccountPermission u where u.account.id = :accountId")
  void deleteByAccountId(@Param("accountId") int accountId);

}
