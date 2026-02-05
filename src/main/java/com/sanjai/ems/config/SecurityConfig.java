package com.sanjai.ems.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth

                // Public APIs
                .requestMatchers("/auth/**").permitAll()

                // Employee APIs - READ
                .requestMatchers(HttpMethod.GET, "/employees/**")
                .hasAnyRole("USER", "ADMIN")

                // Employee APIs - WRITE
                .requestMatchers(HttpMethod.POST, "/employees")
                .hasRole("ADMIN")

                .requestMatchers(HttpMethod.PUT, "/employees/**")
                .hasRole("ADMIN")

                .requestMatchers(HttpMethod.DELETE, "/employees/**")
                .hasRole("ADMIN")

                .anyRequest().authenticated()
            )
            .httpBasic(); // simple for now

        return http.build();
    }

    // In-memory users with roles
    @Bean
    public InMemoryUserDetailsManager userDetailsService() {

        UserDetails user = User
                .withUsername("user")
                .password("{noop}user123")
                .roles("USER")
                .build();

        UserDetails admin = User
                .withUsername("admin")
                .password("{noop}admin123")
                .roles("ADMIN")
                .build();

        return new InMemoryUserDetailsManager(user, admin);
    }
}
