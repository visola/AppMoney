package com.appmoney.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.appmoney.model.ForecastEntry;

@Component
public class ForecastEntryDao {

  @Autowired
  private NamedParameterJdbcTemplate jdbcTemplate;

  @Transactional
  public ForecastEntry insert(ForecastEntry entry) {
    KeyHolder keyHolder = new GeneratedKeyHolder();
    String sql = "INSERT INTO forecast_entries "
        + "(forecast_id, title, amount, category_id, created, created_by, updated, updated_by) VALUES "
        + "(:forecastId, :title, :amount, :categoryId, :created, :createdBy, :updated, :updatedBy)";

    jdbcTemplate.update(sql, new BeanPropertySqlParameterSource(entry), keyHolder);
    entry.setId((int) keyHolder.getKeys().get("id"));
    return entry;
  }

  public List<ForecastEntry> getEntries(int forecastId) {
    String sql = "SELECT *"
        + " FROM forecast_entries"
        + " WHERE forecast_id = :forecastId"
        + " ORDER BY created_by DESC";

    return jdbcTemplate.query(sql, new MapSqlParameterSource("forecastId" , forecastId),  new BeanPropertyRowMapper<>(ForecastEntry.class));
  }

  public ForecastEntry findById(int itemId) {
    String sql = "SELECT *"
        + " FROM forecast_entries"
        + " WHERE id = :itemId"
        + " ORDER BY created_by DESC";

    return jdbcTemplate.queryForObject(
        sql,
        new MapSqlParameterSource("itemId" , itemId),
        new BeanPropertyRowMapper<>(ForecastEntry.class)
    );
  }

  public ForecastEntry update(ForecastEntry entry) {
    String sql = "UPDATE forecast_entries"
        + " SET forecast_id = :forecastId,"
        + " title = :title,"
        + " amount = :amount,"
        + " category_id = :categoryId,"
        + " created = :created,"
        + " created_by = :createdBy,"
        + " updated = :updated,"
        + " updated_by = :updatedBy"
        + " WHERE id = :id";

    jdbcTemplate.update(sql, new BeanPropertySqlParameterSource(entry));
    return entry;
  }

}
