package com.appmoney.integrationtest.glue;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.flywaydb.core.Flyway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import cucumber.api.java.Before;

public class DatabaseCleanerHook extends BaseSteps {

  private static final Logger LOGGER = LoggerFactory.getLogger(DatabaseCleanerHook.class);

  @Autowired
  DataSource dataSource;

  @Autowired
  Flyway flyway;

  @Before
  public void cleanDatabase() throws SQLException {
    boolean tableFound = true;

    while (tableFound) {
      try (Connection conn = dataSource.getConnection()) {
        tableFound = false;
        DatabaseMetaData md = conn.getMetaData();
        ResultSet rs = md.getTables(null, "public", "%", new String[] {"TABLE"});
        while (rs.next()) {
          tableFound = true;
          String tableName = rs.getString("TABLE_NAME");
          try {
            LOGGER.debug("Dropping table: {}", tableName);
            conn.createStatement().executeUpdate("drop table " + tableName);
          } catch (SQLException sqle) {
            LOGGER.debug("Error while deleting table: {}, {}", tableName, sqle.getMessage());
          }
        }
      }
    }

    flyway.migrate();
  }

}
