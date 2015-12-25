package com.appmoney.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import com.appmoney.model.Category;

@Component
public class CategoryDao {
  
  @Autowired
  private NamedParameterJdbcTemplate jdbcTemplate;

  public List<Category> getCategories(int userId) {
    return jdbcTemplate.query("SELECT c.*, cu.active, cu.hidden"
        + " FROM categories c"
        + " LEFT OUTER JOIN categories_users cu"
        + " ON c.id = cu.category_id AND cu.user_id = :userId"
        + " WHERE (c.created_by IS NULL"
        + " OR cu.active = true)"
        + " AND cu.hidden <> true"
        + " ORDER BY name", new MapSqlParameterSource("userId" , userId),  new BeanPropertyRowMapper<>(Category.class));
  }

}
