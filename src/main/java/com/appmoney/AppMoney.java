package com.appmoney;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.appmoney.dao.AccountDAO;
import com.appmoney.dao.AccountDAOImpl;
import com.appmoney.dao.UserDao;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootApplication
public class AppMoney {

  public static void main(String[] args) {
    SpringApplication.run(AppMoney.class, args);    
  }
  
  @Bean
  public AccountDAO accountDAO(){
    return new AccountDAOImpl();
  }

  @Bean
  public HttpClient httpClient() {
    return HttpClients.createDefault();
  }

  @Bean
  public ObjectMapper objectMapper() {
    return new ObjectMapper();
  }

  @Bean
  public UserDao userDao() {
    return new UserDao();
  }

}
