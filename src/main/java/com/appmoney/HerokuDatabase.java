package com.appmoney;

import java.net.URI;
import java.net.URISyntaxException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.zaxxer.hikari.HikariDataSource;

@Configuration
@Profile("heroku")
public class HerokuDatabase {

  @Value("${database.url}")
  String databaseUrl;

  @Bean
  public DataSource dataSource() throws URISyntaxException {
    URI uri = new URI(databaseUrl);

    String username = uri.getUserInfo().split(":")[0];
    String password = uri.getUserInfo().split(":")[1];
    String jdbcUrl = "jdbc:postgresql://" + uri.getHost() + uri.getPath();

    HikariDataSource dataSource = new HikariDataSource();
    dataSource.setJdbcUrl(jdbcUrl);
    dataSource.setUsername(username);
    dataSource.setPassword(password);

    return dataSource;
    
  }

}
