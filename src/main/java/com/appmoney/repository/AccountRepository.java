package com.appmoney.repository;

import java.util.Optional;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.appmoney.model.Account;

public interface AccountRepository extends PagingAndSortingRepository<Account, Integer> {

  Optional<Account> findById(Integer accountId);

}
