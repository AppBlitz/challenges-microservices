package com.authService.auth_service.services;

import com.authService.auth_service.model.User;
import com.authService.auth_service.repository.UserRepository;
import com.authService.auth_service.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    public String login(String email, String rawPassword) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (!user.isEnabled()) {
            throw new RuntimeException("Usuario inhabilitado");
        }

        if (!passwordEncoder.matches(rawPassword, user.getPassword())) {
            throw new RuntimeException("Credenciales inválidas");
        }

        return jwtUtil.generateToken(user.getEmail(), user.getRole());
    }

    public String recoverPassword(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (!user.isEnabled()) {
            throw new RuntimeException("Usuario inhabilitado");
        }

        // Generar token de recuperación (JWT corto)
        String resetToken = jwtUtil.generateResetToken(user.getEmail());

        // Aquí se publicaría un evento usuario.recuperacion (RabbitMQ/Kafka)
        // con el email y el token

        return resetToken;
    }

    public void resetPassword(String token, String newPassword) {
        Claims claims = jwtUtil.validateToken(token);

        if (!"RESET_PASSWORD".equals(claims.get("type", String.class))) {
            throw new RuntimeException("Token inválido para reset");
        }

        String email = claims.getSubject();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }

}
