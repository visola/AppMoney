package com.appmoney;

import java.io.IOException;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.resource.PathResourceResolver;

@SpringBootApplication
public class AppMoney extends WebMvcConfigurerAdapter {

  public static void main(String[] args) {
    SpringApplication.run(AppMoney.class, args);    
  }

  @Bean
  public HttpClient httpClient() {
    return HttpClients.createDefault();
  }

  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    // Static configuration to support Backbone's push state

    // All resources go to where they should go
    registry
      .addResourceHandler("/**/*.css", "/**/*.html", "/**/*.js", "/**/*.jsx", "/**/*.ttf", "/**/*.woff", "/**/*.woff2")
      .addResourceLocations("classpath:/static/");

    // Anything else, goes to index.html
    registry
      .addResourceHandler("/", "/**")
      .addResourceLocations("classpath:/static/index.html").resourceChain(true)
      .addResolver(new PathResourceResolver() {
        @Override
        protected Resource getResource(String resourcePath, Resource location) throws IOException {
          return location.exists() && location.isReadable() ? location : null;
        }
      });
  }

}
