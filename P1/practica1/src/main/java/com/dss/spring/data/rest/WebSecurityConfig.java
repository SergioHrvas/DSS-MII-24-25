package com.dss.spring.data.rest;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

	@Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http,  HandlerMappingIntrospector introspector) throws Exception {
		MvcRequestMatcher.Builder mvcMatcherBuilder = new MvcRequestMatcher.Builder(introspector);
        http
            .authorizeHttpRequests(authorize -> 
                authorize
                .requestMatchers(mvcMatcherBuilder.pattern("/"), mvcMatcherBuilder.pattern("/index.html"), mvcMatcherBuilder.pattern("/h2-console/**"), mvcMatcherBuilder.pattern("/productos")).permitAll() // Permitir acceso sin autenticación a la consola H2
                .requestMatchers(mvcMatcherBuilder.pattern("/admin/**")).hasRole("ADMIN")
                .requestMatchers(mvcMatcherBuilder.pattern("/nuevo-producto"), mvcMatcherBuilder.pattern("/editar-producto/**"), mvcMatcherBuilder.pattern("/api/cart/**"), mvcMatcherBuilder.pattern("/api/products/**")).authenticated()  
               .anyRequest().authenticated() // Requiere autenticación para cualquier otro endpoint
            )
            .formLogin(formLogin -> formLogin.loginPage("/login").defaultSuccessUrl("/productos", true).permitAll())
            .logout(logout->logout.permitAll()
            		
            ).csrf(csrf -> csrf.disable()) // Deshabilitar CSRF para simplificar el acceso a H2 Console
            ;
        // Deshabilitar restricciones de frame para que H2 funcione correctamente
      //  http.headers(headers -> headers.frameOptions(frameOptions -> frameOptions.disable()));
        
        return http.build();

    }
   
}