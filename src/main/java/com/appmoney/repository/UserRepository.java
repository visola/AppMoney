package com.appmoney.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.appmoney.model.User;

public interface UserRepository extends PagingAndSortingRepository<User, Integer> {

  boolean existsByUsername(String username);

  User findByUsername(String username);

}
