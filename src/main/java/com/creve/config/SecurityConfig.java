/*
package com.creve.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // API development ke liye CSRF disable
                .authorizeHttpRequests(auth -> auth
                        // Sabhi zaroori endpoints aur naya /api/auth/** permitAll() mein add kar diya
                        .requestMatchers(
                                "/",
                                "/login/**",
                                "/error",
                                "/api/users/**",
                                "/api/reviews/**",
                                "/api/companies/**",
                                "/api/auth/**" // <-- Registration aur Login ke liye zaroori
                        ).permitAll()
                        .anyRequest().authenticated()
                )
                .oauth2Login(oauth2 -> oauth2
                        .defaultSuccessUrl("/login/success", true)
                );

        return http.build();
    }
}

 */
package com.creve.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                // 1. CSRF disable kiya kyunki JWT stateless hai
                .csrf(csrf -> csrf.disable())

                // 2. Stateless session policy (Production rule)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                // 3. Endpoint Security Rules
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/", "/login/**", "/error", "/api/auth/**", "/api/reviews/**").permitAll() // Login/Signup open
                        .anyRequest().authenticated() // Baaki sab secure
                )

                // 4. Custom JWT Filter ko UsernamePasswordAuthenticationFilter ke pehle lagaya
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)

                .build();
    }
}
