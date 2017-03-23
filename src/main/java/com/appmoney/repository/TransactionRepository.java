package com.appmoney.repository;

import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.appmoney.model.Transaction;

public interface TransactionRepository extends PagingAndSortingRepository<Transaction, Integer> {

  Optional<Transaction> findById(Integer transactionId);

  @Query("SELECT t"
      + " FROM Transaction t"
      + " WHERE t.happened BETWEEN :start AND :end"
      + " AND t.deleted IS NULL"
      + " AND ("
      + "   t.toAccount.id IN (:accountIds)"
      + "   OR t.fromAccount.id IN (:accountIds)"
      + ") ORDER BY happened DESC, created DESC")
  List<Transaction> findByHappenedBetween(@Param("start") Calendar start, @Param("end") Calendar end, @Param("accountIds") Set<Integer> accountIds);

  @Query("SELECT t"
      + " FROM Transaction t"
      + " WHERE ("
      + "   t.toAccount.id IN (:accountIds)"
      + "   OR t.fromAccount.id IN (:accountIds)"
      + ") AND t.deleted IS NULL"
      + " ORDER BY happened DESC, created DESC")
  Page<Transaction> getRecentTransactions(@Param("accountIds") Set<Integer> accountIds, Pageable pageRequest);

}
