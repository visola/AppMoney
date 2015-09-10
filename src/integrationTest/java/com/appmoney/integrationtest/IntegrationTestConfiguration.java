package com.appmoney.integrationtest;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("integrationTest")
public class IntegrationTestConfiguration {

  private static final Logger logger = LoggerFactory.getLogger(IntegrationTestConfiguration.class);

  @Bean
  public SeleniumHelper seleniumHelper() {
    return new SeleniumHelper();
  }

  @Bean
  public WebDriver webDriver() throws Exception {
    logger.info("Initializing WebDriver");
    return new ChromeDriver();
  }

}
