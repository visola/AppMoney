package com.appmoney.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import com.appmoney.model.Category;

@Component
public class CategoryDao {
  
  @Autowired
  private NamedParameterJdbcTemplate jdbcTemplate;

  public List<Category> getCategories() {
    return jdbcTemplate.query("SELECT * FROM categories ORDER BY name", new BeanPropertyRowMapper<>(Category.class));
  }

}
