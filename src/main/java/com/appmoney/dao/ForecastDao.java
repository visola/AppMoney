package com.appmoney.dao;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.appmoney.model.Forecast;
import com.appmoney.model.Permission;

@Component
public class ForecastDao {

  private static final String SELECT_PERMISSIONS_BY_USER_ID_AND_FORECAST_ID = "SELECT permission"
      + " FROM forecast_permissions"
      + " WHERE user_id = :userId"
      + " AND forecast_id = :forecastId";

  @Autowired
  private NamedParameterJdbcTemplate jdbcTemplate;

  @Transactional
  public Forecast insert(Forecast forecast) {
    KeyHolder keyHolder = new GeneratedKeyHolder();
    String sql = "INSERT INTO forecasts "
        + "(start_day_of_month, created, created_by, updated, updated_by) VALUES "
        + "(:startDayOfMonth, :created, :createdBy, :updated, :updatedBy)";

    jdbcTemplate.update(sql, new BeanPropertySqlParameterSource(forecast), keyHolder);
    forecast.setId((int) keyHolder.getKeys().get("id"));

    insertPermissions(forecast);

    return forecast;
  }

  public Optional<Forecast> getForUser(int userId) {
    String sql = "SELECT f.*"
        + " FROM forecasts f"
        + " WHERE EXISTS (SELECT 1 FROM forecast_permissions WHERE user_id = :userId AND forecast_id = f.id)";

    MapSqlParameterSource paramMap = new MapSqlParameterSource("userId" , userId);

    try {
      Forecast forecast = jdbcTemplate.queryForObject(sql, paramMap, new BeanPropertyRowMapper<>(Forecast.class));

      paramMap.addValue("forecastId", forecast.getId());
      forecast.setPermissions(jdbcTemplate.query(SELECT_PERMISSIONS_BY_USER_ID_AND_FORECAST_ID, paramMap, new PermissionRowMapper()));
      return Optional.of(forecast);
    } catch (EmptyResultDataAccessException e) {
      return Optional.empty();
    }
  }

  public Optional<Forecast> findById(int forecastId, int userId) {
    String sql = "SELECT f.*"
        + " FROM forecasts f"
        + " WHERE id = :forecastId"
        + " AND EXISTS (SELECT 1 FROM forecast_permissions WHERE user_id = :userId AND forecast_id = f.id)";

    MapSqlParameterSource paramMap = new MapSqlParameterSource("userId" , userId);
    paramMap.addValue("forecastId", forecastId);

    try {
      Forecast forecast = jdbcTemplate.queryForObject(sql, paramMap, new BeanPropertyRowMapper<>(Forecast.class));
  
      paramMap.addValue("forecastId", forecast.getId());
      forecast.setPermissions(jdbcTemplate.query(SELECT_PERMISSIONS_BY_USER_ID_AND_FORECAST_ID, paramMap, new PermissionRowMapper()));
      return Optional.of(forecast);
    } catch (EmptyResultDataAccessException e) {
      return Optional.empty();
    }
  }

  private void insertPermissions(Forecast forecast) {
    MapSqlParameterSource paramMap = new MapSqlParameterSource("userId", forecast.getUpdatedBy());
    paramMap.addValue("forecastId", forecast.getId());
    for (Permission permission : forecast.getPermissions()) {
      paramMap.addValue("permission", permission.toString());
      jdbcTemplate.update("INSERT INTO forecast_permissions (user_id, forecast_id, permission) VALUES (:userId, :forecastId, :permission)", paramMap);
    }
  }

}
