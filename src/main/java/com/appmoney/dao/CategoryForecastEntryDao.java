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

import com.appmoney.model.CategoryForecastEntry;

@Component
public class CategoryForecastEntryDao {

  @Autowired
  private NamedParameterJdbcTemplate jdbcTemplate;

  @Transactional
  public CategoryForecastEntry insert(CategoryForecastEntry entry) {
    KeyHolder keyHolder = new GeneratedKeyHolder();
    String sql = "INSERT INTO category_forecast_entries "
        + "(forecast_id, title, amount, category_id, created, created_by, updated, updated_by) VALUES "
        + "(:forecastId, :title, :amount, :categoryId, :created, :createdBy, :updated, :updatedBy)";

    jdbcTemplate.update(sql, new BeanPropertySqlParameterSource(entry), keyHolder);
    entry.setId((int) keyHolder.getKeys().get("id"));
    return entry;
  }

  public List<CategoryForecastEntry> getEntries(int forecastId) {
    String sql = "SELECT *"
        + " FROM category_forecast_entries"
        + " WHERE forecast_id = :forecastId"
        + " ORDER BY created_by DESC";
    
    return jdbcTemplate.query(sql, new MapSqlParameterSource("forecastId" , forecastId),  new BeanPropertyRowMapper<>(CategoryForecastEntry.class));
  }

}
