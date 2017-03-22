package com.appmoney.repository;

import java.util.Optional;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.appmoney.model.ForecastPermission;

public interface ForecastPermissionRepository extends PagingAndSortingRepository<ForecastPermission, Integer> {

  Optional<ForecastPermission> findByForecastIdAndUserId(int userId, int forecastId);

  Optional<ForecastPermission> findByUserId(Integer id);

}
