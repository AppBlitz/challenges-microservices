package com.authService.auth_service.services;

import com.authService.auth_service.model.User;
import com.authService.auth_service.repository.UserRepository;
import com.authService.auth_service.utils.JwtUtil;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class EmployeeEventListener {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    public EmployeeEventListener(UserRepository userRepository, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
    }

    @RabbitListener(queues = "empleado.creado")
    public void handleEmployeeCreated(String email) {
        // Crear usuario con rol USER y sin contraseña válida
        User user = User.builder()
                .email(email)
                .password("") // aún no tiene contraseña
                .role("USER")
                .enabled(true)
                .build();
        userRepository.save(user);

        // Generar token de recuperación
        String resetToken = jwtUtil.generateResetToken(email);

        // Publicar evento usuario.creado
        // (ejemplo simple, en producción se usaría RabbitTemplate)
        System.out.println("[EVENTO] usuario.creado -> " + email + " token=" + resetToken);
    }

    @RabbitListener(queues = "empleado.eliminado")
    public void handleEmployeeDeleted(String email) {
        userRepository.findByEmail(email).ifPresent(user -> {
            user.setEnabled(false);
            userRepository.save(user);
        });
        System.out.println("[EVENTO] usuario.inhabilitado -> " + email);
    }
}
