package com.proyect.codegodot.Config;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import lombok.extern.slf4j.Slf4j;

/**
 * Configuración global de CORS para permitir peticiones desde el frontend
 * Permite que tanto el frontend local como el frontend en S3 accedan a la API
 */
@Configuration
@Slf4j
public class CorsConfig {

    @Value("${cors.allowed.origins}")
    private String allowedOrigins;

    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();
        
        // Parsear orígenes permitidos desde properties
        Set<String> origins = new HashSet<>();
        if (allowedOrigins != null && !allowedOrigins.isEmpty()) {
            String[] originArray = allowedOrigins.split(",");
            for (String origin : originArray) {
                String trimmed = origin.trim();
                origins.add(trimmed);
                log.debug("CORS origin configured: {}", trimmed);
            }
        }
        
        // Establecer los orígenes permitidos
        config.setAllowedOrigins(new java.util.ArrayList<>(origins));
        
        // Configurar permisos
        config.setAllowCredentials(false);
        config.setAllowedHeaders(Arrays.asList("*"));
        config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS", "HEAD"));
        config.setMaxAge(3600L);
        
        // Registrar la configuración CORS para todos los endpoints de API
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/api/**", config);
        
        log.info("✅ CORS Filter initialized with {} origins", origins.size());
        return new CorsFilter(source);
    }
}