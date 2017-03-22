package com.appmoney.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.appmoney.model.Forecast;

public interface ForecastRepository extends PagingAndSortingRepository<Forecast, Integer> {

}
