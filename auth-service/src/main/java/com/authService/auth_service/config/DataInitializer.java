package com.authService.auth_service.config;

import com.authService.auth_service.model.User;
import com.authService.auth_service.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner init(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            if (userRepository.findByEmail("admin@empresa.com").isEmpty()) {
                User admin = User.builder()
                        .email("admin@empresa.com")
                        .password(passwordEncoder.encode("admin123"))
                        .role("ADMIN")
                        .enabled(true)
                        .build();
                userRepository.save(admin);
            }
        };
    }
}
