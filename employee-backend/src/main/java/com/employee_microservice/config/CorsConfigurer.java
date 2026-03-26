package com.employee_microservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Configuration class for Cross-Origin Resource Sharing (CORS) settings.
 * This class defines the security rules for external access to the 
 * employee microservice endpoints.
 */
@Configuration
public class CorsConfigurer {

    /**
     * Configures the mapping of CORS rules for specific API endpoints.
     * * @return A {@link WebMvcConfigurer} bean that specifies the allowed origins 
     * and methods for the microservice routes.
     */
    @Bean("configurerCors")
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            /**
             * Configures global CORS mapping for the application.
             * * @param corsRegistry The registry used to define mapping rules.
             */
            @Override
            public void addCorsMappings(CorsRegistry corsRegistry) {
                // Allows POST requests for the update endpoint
                corsRegistry.addMapping("/employee/update")
                            .allowedOrigins()
                            .allowedMethods("POST");
                
                // Allows GET requests for the retrieve endpoint
                corsRegistry.addMapping("/employee/retrieve")
                            .allowedOrigins()
                            .allowedMethods("GET");
                
                // Allows GET requests for the test endpoint
                corsRegistry.addMapping("/employee/test")
                            .allowedOrigins()
                            .allowedMethods("GET");
            }
        };
    }
}
