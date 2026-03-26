package com.example.demo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.*;

import com.example.demo.jwt.JwtFilter;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.List;

@Configuration
public class SecurityConfig {

    private final JwtFilter jwtFilter;

    public SecurityConfig(JwtFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        System.out.println("🔐 Security ENABLED with JWT");

        http
            .cors(cors -> cors.configurationSource(request -> {
                CorsConfiguration config = new CorsConfiguration();
                config.setAllowCredentials(true);
                config.setAllowedOrigins(List.of(
                    "https://69c3c62b0c7bd80df9316141--frontend-emp-management.netlify.app"
                ));
                config.setAllowedHeaders(List.of("*"));
                config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
                return config;
            }))
            .csrf(csrf -> csrf.disable())
            .sessionManagement(session -> session
            	    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            	)
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/login", "/register").permitAll()
                
                // 🔐 ROLE-BASED SECURITY
                .requestMatchers("/admin/**").hasRole("ADMIN")
                .requestMatchers("/user/**").hasAnyRole("USER", "ADMIN")
                .requestMatchers("/employees/**").authenticated()
                .anyRequest().authenticated()
            )

            // 🔥 ADD JWT FILTER HERE
            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)

            .httpBasic(httpBasic -> httpBasic.disable())
            .formLogin(form -> form.disable());

        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}