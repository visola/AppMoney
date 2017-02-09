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

import com.appmoney.model.Category;

@Component
public class CategoryDao {
  
  @Autowired
  private NamedParameterJdbcTemplate jdbcTemplate;

  public List<Category> getCategories(int userId, Boolean includeHidden) {
    String sql = "SELECT c.*, cu.hidden"
        + " FROM categories c"
        + " LEFT OUTER JOIN shared_categories friends"
        + "   ON ("
        + "     NOT c.created_by IS NULL"
        + "     AND friends.owner_id = c.created_by"
        + "   )"
        + " LEFT OUTER JOIN categories_users cu"
        + "   ON ("
        + "     c.id = cu.category_id"
        + "     AND ("
        + "       cu.user_id = :userId"
        + "       OR cu.user_id = friends.owner_id"
        + "     )"
        + "  )"
        + " WHERE ("
        + "   c.created_by IS NULL"
        + "   OR c.created_by = :userId"
        + "   OR friends.friend_id = :userId"
        + ")";
    if (includeHidden != true) {
      sql += " AND (cu.hidden IS NULL OR cu.hidden <> true)";
    }
    sql += " ORDER BY name";
    return jdbcTemplate.query(sql, new MapSqlParameterSource("userId" , userId),  new BeanPropertyRowMapper<>(Category.class));
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

  public void createUserRecords(int userId) {
    jdbcTemplate.update("INSERT INTO categories_users (SELECT c.id, :userId, false FROM categories c)", new MapSqlParameterSource("userId" , userId));
  }

  @Transactional
  public Category update(Category category, int userId) {
    String sql = "UPDATE categories SET"
        + " (name, parent_id, created_by) ="
        + " (:name, :parentId, :createdBy)"
        + " WHERE id = :id";

    jdbcTemplate.update(sql, new BeanPropertySqlParameterSource(category));

    sql = "UPDATE categories_users SET"
        + " (hidden) = "
        + " (:hidden)"
        + " WHERE category_id = :id"
        + " AND user_id = " + userId;

    jdbcTemplate.update(sql, new BeanPropertySqlParameterSource(category));

    return category;
  }

}
