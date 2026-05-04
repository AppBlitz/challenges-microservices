package com.authService.auth_service.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.authService.auth_service.model.User;
import com.authService.auth_service.repository.UserRepository;

@Configuration
public class DataInitializer {

    @Value("${auth.service.email}")
    private String email;

    @Value("${auth.service.password}")
    private String password;

    @Value("${auth.service.role}")
    private String role;

    @Bean
    CommandLineRunner init(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            if (userRepository.findByEmail(email).isEmpty()) {
                User admin = User.builder()
                        .email(email)
                        .password(passwordEncoder.encode(password))
                        .role(role)
                        .enabled(true)
                        .build();
                userRepository.save(admin);
            }
        };
    }
}
