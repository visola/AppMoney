package com.appmoney.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled=true)
@EnableWebSecurity
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

  @Autowired
  TokenService tokenService;

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
      .csrf().disable()
      .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)

      .and()
      .authorizeRequests()
      .antMatchers("/authenticate").anonymous()
      .antMatchers(ACTUATOR_ENDPOINTS).hasRole(Roles.ADMIN.toString())
      .anyRequest().authenticated();

    http.addFilterBefore(tokenAuthenticationFilter(tokenService), BasicAuthenticationFilter.class);
  }

  private TokenAuthenticationFilter tokenAuthenticationFilter(TokenService tokenService) {
    return new TokenAuthenticationFilter(tokenService);
  }

}
