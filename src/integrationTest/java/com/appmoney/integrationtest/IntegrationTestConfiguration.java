package com.appmoney.integrationtest;

import javax.sql.DataSource;

import org.flywaydb.core.Flyway;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
@Profile("integrationTest")
@PropertySource("classpath:application.yaml")
public class IntegrationTestConfiguration {

  private static final Logger logger = LoggerFactory.getLogger(IntegrationTestConfiguration.class);

  @Autowired
  Environment environment;

  @Bean
  public DataSource dataSource() {
    HikariConfig config = new HikariConfig();
    config.setJdbcUrl(environment.getProperty("spring.datasource.url"));
    config.setUsername(environment.getProperty("spring.datasource.username"));
    config.setPassword(environment.getProperty("spring.datasource.password"));
    config.setDriverClassName(environment.getProperty("spring.datasource.driverClassName"));

    return new HikariDataSource(config);
  }

  @Bean
  public Flyway flyway() {
    Flyway flyway = new Flyway();
    flyway.setDataSource(dataSource());
    return flyway;
  }

  @Bean
  public SeleniumHelper seleniumHelper(WebDriver driver) {
    return new SeleniumHelper(driver);
  }

  @Bean
  public WebDriver webDriver() throws Exception {
    logger.info("Initializing WebDriver");
    return new ChromeDriver();
  }

}
