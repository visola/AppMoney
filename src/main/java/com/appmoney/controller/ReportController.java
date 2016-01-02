package com.appmoney.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.appmoney.model.User;

@RequestMapping("/api/v1/reports")
@RestController
public class ReportController {

  @Autowired
  NamedParameterJdbcTemplate jdbcTemplate;

  @RequestMapping("/totals/per-category")
  public List<Map<String, Object>> perCategory(@AuthenticationPrincipal User user) {
    String query = "SELECT account, category, year, month, day, ROUND(SUM(ABS(value)), 2) as total"
        + " FROM ("
        + "   SELECT DISTINCT a.name as account, c.name as category,"
        + "   DATE_PART('year', t.happened) as year,"
        + "   DATE_PART('month', t.happened) - 1 as month,"
        + "   DATE_PART('day', t.happened) as day,"
        + "   t.value as value"
        + "   FROM transactions t"
        + "   JOIN categories c ON c.id = t.category_id"
        + "   JOIN permissions p ON p.account_id = t.to_account_id AND t.from_account_id IS NULL"
        + "   JOIN accounts a ON a.id = t.to_account_id"
        + "   WHERE p.user_id = :userId"
        + "   AND t.value < 0"
        + " ) as t1"
        + " GROUP BY account, category, year, month, day";

    return jdbcTemplate.queryForList(query, new MapSqlParameterSource("userId" , user.getId()));
  }

}
