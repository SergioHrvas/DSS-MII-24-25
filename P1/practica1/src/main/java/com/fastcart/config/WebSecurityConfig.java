package com.fastcart.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

import com.fastcart.service.impl.UserDetailsServiceImpl;

import static org.springframework.boot.autoconfigure.security.servlet.PathRequest.toH2Console;

import org.springframework.beans.factory.annotation.Autowired;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
    @Autowired
    UserDetailsServiceImpl userDetailsService;
    
	@Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http,  HandlerMappingIntrospector introspector) throws Exception {
		MvcRequestMatcher.Builder mvcMatcherBuilder  = new MvcRequestMatcher.Builder(introspector);
        http  
        	.headers(headers -> headers
               .frameOptions(frameOptions -> frameOptions.disable()) // Deshabilitar restricciones de uso de iframes
        	    )
            .authorizeHttpRequests(authorize -> 
                authorize
                .requestMatchers(toH2Console()).permitAll()
                .requestMatchers(mvcMatcherBuilder.pattern("/"), mvcMatcherBuilder.pattern("/index.html"), mvcMatcherBuilder.pattern("/h2-console/**"), mvcMatcherBuilder.pattern("products/searchAndFilter"), mvcMatcherBuilder.pattern("/css/**"), mvcMatcherBuilder.pattern("/products"), mvcMatcherBuilder.pattern("/user/register"),mvcMatcherBuilder.pattern("/register"), mvcMatcherBuilder.pattern("/error"), mvcMatcherBuilder.pattern("/favicon.ico")).permitAll() // Permitir acceso sin autenticación a la consola H2
                .requestMatchers(mvcMatcherBuilder.pattern("/admin/**")).hasRole("ADMIN")
                .requestMatchers(mvcMatcherBuilder.pattern("/cart"), mvcMatcherBuilder.pattern("/cart/**")).authenticated()  
                .anyRequest().authenticated() // Requiere autenticación para cualquier otro endpoint
            )
            .formLogin(formLogin -> formLogin.loginPage("/login").defaultSuccessUrl("/index.html", true).permitAll())
            .logout(logout->logout.permitAll()
            		
            ).csrf(csrf -> csrf.disable()) // Deshabilitar CSRF para simplificar el acceso a H2 Console
            ;
        // Deshabilitar restricciones de frame para que H2 funcione correctamente
      //  http.headers(headers -> headers.frameOptions(frameOptions -> frameOptions.disable()));
        
        return http.build();

	}
	 @Bean
	    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
	        return authenticationConfiguration.getAuthenticationManager();
	    }

	    @Bean
	    public PasswordEncoder passwordEncoder() {
	        return new BCryptPasswordEncoder();
	    }

	    @Bean
	    public DaoAuthenticationProvider authenticationProvider() {
	        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
	        authProvider.setUserDetailsService(userDetailsService);
	        authProvider.setPasswordEncoder(passwordEncoder());
	        return authProvider;
	    }
    
}