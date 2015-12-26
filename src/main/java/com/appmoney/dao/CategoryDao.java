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

import com.appmoney.model.Category;

@Component
public class CategoryDao {
  
  @Autowired
  private NamedParameterJdbcTemplate jdbcTemplate;

  public List<Category> getCategories(int userId) {
    return jdbcTemplate.query("SELECT c.*, cu.active, cu.hidden"
        + " FROM categories c"
        + " LEFT OUTER JOIN categories_users cu"
        + " ON c.id = cu.category_id"
        + " WHERE (c.created_by = :userId OR cu.active = true)"
        + " AND (cu.hidden IS NULL OR cu.hidden <> true)"
        + " ORDER BY name", new MapSqlParameterSource("userId" , userId),  new BeanPropertyRowMapper<>(Category.class));
  }

  public Category create(Category category) {
    KeyHolder keyHolder = new GeneratedKeyHolder();
    String sql = "INSERT INTO categories "
        + "(name, parent_id, created_by) VALUES "
        + "(:name, :parentId, :createdBy)";

    jdbcTemplate.update(sql,  new BeanPropertySqlParameterSource(category), keyHolder);
    category.setId((int) keyHolder.getKeys().get("id"));

    return category;
  }

}
