package com.numan947.pmbackend.common;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.List;

/**
 * BeansConfig is a configuration class that defines various beans for the application.
 * It includes beans for CORS configuration, authentication provider, password encoder, and authentication manager.
 *
 * Fields:
 * - allowedOrigins: A list of allowed origins for CORS, injected from application properties.
 * - userDetailsService: A service for loading user-specific data, injected via constructor.
 *
 * Methods:
 * - corsFilter(): Configures and returns a CorsFilter bean.
 * - authProvider(): Configures and returns an AuthenticationProvider bean.
 * - passwordEncoder(): Configures and returns a PasswordEncoder bean.
 * - authenticationManager(AuthenticationConfiguration config): Configures and returns an AuthenticationManager bean.
 *
 * Annotations:
 * - @Configuration: Indicates that this class contains bean definitions.
 * - @RequiredArgsConstructor: Lombok annotation to generate a constructor with required arguments.
 * - @Value: Injects values from application properties.
 * - @Bean: Indicates that a method produces a bean to be managed by the Spring container.
 */


@Configuration
@RequiredArgsConstructor
public class BeansConfig {
    @Value("${application.security.cors.origins}")
    private List<String> allowedOrigins;
    @Bean
    public CorsFilter corsFilter() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        final CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.setAllowedOrigins(allowedOrigins);
        config.setAllowedHeaders(List.of("Origin", "Content-Type", "Accept", "Authorization"));
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"));
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }


    private final UserDetailsService userDetailsService;
    @Bean
    public AuthenticationProvider authProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

}
