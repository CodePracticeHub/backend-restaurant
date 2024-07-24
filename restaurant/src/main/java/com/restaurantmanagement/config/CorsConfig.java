package com.restaurantmanagement.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.addAllowedOrigin("*"); // Allow requests from any origin (replace with your frontend URL in production)
        config.addAllowedMethod("*"); // Allow all HTTP methods
        config.addAllowedHeader("*"); // Allow all headers
        source.registerCorsConfiguration("/**", config);

        source.registerCorsConfiguration("/**", config);
        return new CorsFilter((CorsConfigurationSource) source);
    }
}


