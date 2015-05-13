package com.appmoney.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled=true)
@EnableWebMvcSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

  private static final String [] ACTUATOR_ENDPOINTS = {
    "/autoconfig",
    "/beans",
    "/configprops",
    "/env",
    "/mappings",
    "/metrics",
    "/shutdown"
  };

  @Value("${admin.password}")
  String adminPassword;

  @Value("${admin.username}")
  String adminUsername;
  
  @Value("${user.password}")
  String userPassword;
  
  @Value("${user.username}")
  String userUsername;

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
      .csrf().disable()
      .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

    http
      .authorizeRequests()
      .antMatchers("/authenticate").anonymous()
      .antMatchers(ACTUATOR_ENDPOINTS).hasRole(Roles.ADMIN.toString())
      .anyRequest().authenticated();

    http.addFilterBefore(tokenAuthenticationFilter(), BasicAuthenticationFilter.class);
  }

  @Autowired
  public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
    auth
      .inMemoryAuthentication()
        .withUser(adminUsername).password(adminPassword).roles(Roles.ADMIN.toString())
        .and().withUser(userUsername).password(userPassword).roles(Roles.USER.toString());
  }

  @Bean
  public TokenAuthenticationFilter tokenAuthenticationFilter() {
    return new TokenAuthenticationFilter(tokenService());
  }

  @Bean
  public TokenService tokenService() {
    return new TokenService();
  }

}
