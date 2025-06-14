package com.eduardo.oficina.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Desativa proteção CSRF (para API REST)
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll() // Permite tudo (temporariamente)
                );
        return http.build();
    }
}
