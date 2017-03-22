package com.appmoney.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.appmoney.model.ForecastEntry;

public interface ForecastEntryRepository extends PagingAndSortingRepository<ForecastEntry, Integer> {

  List<ForecastEntry> findByForecastId(Integer forecastId);

}
