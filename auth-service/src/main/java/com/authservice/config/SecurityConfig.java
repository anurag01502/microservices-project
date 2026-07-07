package com.authservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.authservice.security.JwtAuthFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    
    private final JwtAuthFilter jwtAuthFilter;
    
    

    public SecurityConfig(JwtAuthFilter jwtAuthFilter) {
		super();
		this.jwtAuthFilter = jwtAuthFilter;
	}

	@Bean
    public SecurityFilterChain securityFilterChain( HttpSecurity http)  throws Exception {

        return http

                .csrf( csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth.requestMatchers( "/auth/**").permitAll() .anyRequest().authenticated())
                .sessionManagement(session ->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS) )
                .addFilterBefore( jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    PasswordEncoder passwordEncoder() {

        return new BCryptPasswordEncoder();
    }

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {

        return config .getAuthenticationManager();
    }
}