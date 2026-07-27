package com.example.project_for_security.config;

import com.example.project_for_security.security.JwtFilter;
import com.example.project_for_security.security.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
public class SecurityConfig {
    @Autowired
    private JwtFilter jwtFilter;
    @Bean
    public SecurityFilterChain SecurityFilterChain(HttpSecurity http) throws Exception {
      http.csrf(csrf -> csrf.disable())
              .cors(cors -> {})
              .authorizeHttpRequests(auth-> auth.requestMatchers("/auth/register", "/auth/login").permitAll().anyRequest().authenticated()
              ).addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
     return http.build();
    }
        @Bean
        public PasswordEncoder passwordEncoder() {
            return new BCryptPasswordEncoder();
        }

}
