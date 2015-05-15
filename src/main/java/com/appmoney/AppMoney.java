package com.appmoney;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.appmoney.dao.AccountDAO;
import com.appmoney.dao.AccountDAOImpl;

@SpringBootApplication
public class AppMoney {

  public static void main(String[] args) {
    SpringApplication.run(AppMoney.class, args);    
  }
  
  @Bean
  public AccountDAO accountDAO(){
    return new AccountDAOImpl();
  }

}
