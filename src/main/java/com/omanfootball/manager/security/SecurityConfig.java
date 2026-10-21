
package com.omanfootball.manager.security;

import lombok.RequiredArgsConstructor;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http,
            JwtAuthenticationFilter jwtAuthenticationFilter
    ) throws Exception {

        http
                .csrf(csrf -> csrf.disable())

                .cors(cors -> {})

                .sessionManagement(session ->
                        session.sessionCreationPolicy(
                                SessionCreationPolicy.STATELESS
                        )
                )

                .authorizeHttpRequests(auth -> auth

                        // ==================================
                        // PUBLIC FRONTEND PAGES
                        // ==================================
                        .requestMatchers(
                                "/",
                                "/index.html",
                                "/login.html",
                                "/register.html",
                                "/verify.html",
                                "/forgot-password.html",
                                "/shared-team.html",

                                // User HTML pages
                                "/user/*.html",

                                // Admin HTML pages
                                "/admin/*.html",

                                // Static resources
                                "/css/**",
                                "/js/**",
                                "/assets/**",
                                "/favicon.ico"
                        ).permitAll()

                        // ==================================
                        // PUBLIC AUTHENTICATION API
                        // ==================================
                        .requestMatchers("/auth/**")
                        .permitAll()

                        // ==================================
                        // PUBLIC READ-ONLY API
                        // ==================================
                        .requestMatchers(
                                HttpMethod.GET,
                                "/shared-teams/by-code/**",
                                "/players/**",
                                "/clubs/**",
                                "/matches/**"
                        ).permitAll()

                        // ==================================
                        // USER PROFILE
                        // ==================================
                        .requestMatchers(
                                HttpMethod.GET,
                                "/users/me"
                        ).authenticated()

                        // ==================================
                        // ADMIN API
                        // ==================================
                        .requestMatchers("/admin/**")
                        .hasRole("ADMIN")

                        .requestMatchers("/users/**")
                        .hasRole("ADMIN")

                        // Admin create operations
                        .requestMatchers(
                                HttpMethod.POST,
                                "/players/**",
                                "/clubs/**",
                                "/matches/**"
                        ).hasRole("ADMIN")

                        // Admin update operations
                        .requestMatchers(
                                HttpMethod.PUT,
                                "/players/**",
                                "/clubs/**",
                                "/matches/**"
                        ).hasRole("ADMIN")

                        // Admin delete operations
                        .requestMatchers(
                                HttpMethod.DELETE,
                                "/players/**",
                                "/clubs/**",
                                "/matches/**"
                        ).hasRole("ADMIN")

                        // ==================================
                        // AUTHENTICATED USER RESOURCES
                        // ==================================
                        .requestMatchers(
                                "/fantasy-teams/mine",
                                "/fantasy-teams/mine/**",
                                "/simulations/mine",
                                "/simulations/mine/**"
                        ).authenticated()

                        // ==================================
                        // ADMIN-ONLY RESOURCES
                        // ==================================
                        .requestMatchers(
                                "/performances/**",
                                "/match-statistics/**",
                                "/simulations/**",
                                "/fantasy-teams/**",
                                "/fantasy-team-players/**"
                        ).hasRole("ADMIN")

                        // ==================================
                        // ALL OTHER REQUESTS
                        // ==================================
                        .anyRequest().authenticated()
                );

        // JWT authentication filter
        http.addFilterBefore(
                jwtAuthenticationFilter,
                UsernamePasswordAuthenticationFilter.class
        );

        return http.build();
    }

    // ==================================
    // AUTHENTICATION MANAGER
    // ==================================
    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration configuration
    ) throws Exception {

        return configuration.getAuthenticationManager();
    }

    // ==================================
    // DISABLE DUPLICATE JWT FILTER
    // ==================================
    @Bean
    public FilterRegistrationBean<JwtAuthenticationFilter>
    disableContainerJwtFilter(
            JwtAuthenticationFilter filter
    ) {

        FilterRegistrationBean<JwtAuthenticationFilter> registration =
                new FilterRegistrationBean<>(filter);

        registration.setEnabled(false);

        return registration;
    }

    // ==================================
    // CORS CONFIGURATION
    // ==================================
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {

        CorsConfiguration configuration =
                new CorsConfiguration();

        configuration.setAllowedOriginPatterns(
                List.of("*")
        );

        configuration.setAllowedHeaders(
                List.of("*")
        );

        configuration.setAllowedMethods(
                List.of(
                        "GET",
                        "POST",
                        "PUT",
                        "DELETE",
                        "PATCH",
                        "OPTIONS"
                )
        );

        UrlBasedCorsConfigurationSource source =
                new UrlBasedCorsConfigurationSource();

        source.registerCorsConfiguration(
                "/**",
                configuration
        );

        return source;
    }
}
