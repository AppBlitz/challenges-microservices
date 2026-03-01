package com.employee_microservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfigurer {

  @Bean("configurerCors")
  public WebMvcConfigurer corsConfigurer() {
    return new WebMvcConfigurer() {
      @Override
      public void addCorsMappings(CorsRegistry corsRegistry) {
        corsRegistry.addMapping("/employee/update").allowedOrigins().allowedMethods("POST");
        corsRegistry.addMapping("/employee/retrieve").allowedOrigins().allowedMethods("GET");
        corsRegistry.addMapping("/employee/test").allowedOrigins().allowedMethods("GET");
      }
    };
  }

}
