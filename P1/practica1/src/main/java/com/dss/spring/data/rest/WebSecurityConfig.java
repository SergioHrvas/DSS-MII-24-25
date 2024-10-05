package com.dss.spring.data.rest;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // Deshabilitar CSRF para simplificar el acceso a H2 Console
            .authorizeHttpRequests(authorize -> 
                authorize
                    .requestMatchers(new AntPathRequestMatcher("/h2-console/**")).permitAll() // Permitir acceso sin autenticación a la consola H2
                    .anyRequest().authenticated() // Requiere autenticación para cualquier otro endpoint
            )
            .httpBasic(); // Autenticación básica

        // Deshabilitar restricciones de frame para que H2 funcione correctamente
        http.headers(headers -> headers.frameOptions(frameOptions -> frameOptions.disable()));

        return http.build();
    }
}