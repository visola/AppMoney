package com.appmoney.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.appmoney.model.User;
import com.appmoney.model.UserService;

public interface UserRepository extends PagingAndSortingRepository<User, Integer>, UserService {

  @Query("select u from User u where u.username = :username")
  User loadUserByUsername(@Param("username") String username);

  @Modifying
  @Query(nativeQuery=true, value = "insert into users (username) values (?1)")
  void create(String username);

  @Query("select count(1) > 0 from User u where u.username = :username")
  boolean exists(@Param("username") String username);

}
