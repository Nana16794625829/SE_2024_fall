package com.isslab.se_form_backend.config;

import com.isslab.se_form_backend.controller.AuthController;
import com.isslab.se_form_backend.repository.StudentRepository;
import com.isslab.se_form_backend.security.JwtUtil;
import com.isslab.se_form_backend.security.filter.JwtAuthFilter;
import com.isslab.se_form_backend.security.service.StudentUserDetailsService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    @Value("${jwt.secretKey}")
    private String secretKey;

    @Bean
    public JwtUtil jwtUtil(){
        return new JwtUtil(secretKey);
    }

    @Bean
    public JwtAuthFilter jwtAuthFilter(JwtUtil jwtUtil){
        return new JwtAuthFilter(jwtUtil);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, JwtAuthFilter jwtAuthFilter) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/login").permitAll() // login 不需驗證
                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public StudentUserDetailsService studentUserDetailsService(StudentRepository studentRepository){
        return new StudentUserDetailsService(studentRepository);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http,PasswordEncoder passwordEncoder, StudentUserDetailsService userDetailsService)
            throws Exception {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder);

        AuthenticationManagerBuilder builder =
                http.getSharedObject(AuthenticationManagerBuilder.class);
        builder.authenticationProvider(provider);

        return builder.build();
    }

    @Bean
    public AuthController authController(AuthenticationManager authenticationManager, JwtUtil jwtUtil){
        return new AuthController(authenticationManager, jwtUtil);
    }
}