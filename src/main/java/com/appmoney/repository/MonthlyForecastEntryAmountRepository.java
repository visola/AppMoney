package com.appmoney.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.appmoney.model.MonthlyForecastEntryAmount;

public interface MonthlyForecastEntryAmountRepository extends PagingAndSortingRepository<MonthlyForecastEntryAmount, Integer> {

  @Query("select m from MonthlyForecastEntryAmount m join m.forecastEntry e where e.forecastId = :forecastId")
  List<MonthlyForecastEntryAmount> findByForecastId(@Param("forecastId") Integer id);

  @Query("delete from MonthlyForecastEntryAmount m where m.forecastEntry.id = :forecastEntryId")
  @Modifying
  void deleteByForecastEntryId(@Param("forecastEntryId") int forecastEntryId);

}
