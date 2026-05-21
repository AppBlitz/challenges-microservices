package com.authService.auth_service.services;

import com.authService.auth_service.model.User;
import com.authService.auth_service.repository.UserRepository;
import com.authService.auth_service.utils.JwtUtil;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class EmployeeEventListener {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Value("${event_one}")
    private String eventOne;

    @Value("${event_two}")
    private String eventTwo;

    public EmployeeEventListener(UserRepository userRepository, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
    }

    @RabbitListener(queues = "auth.employee")
    public void handleEmployeeEvent(Message message) throws Exception {
        String routingKey = message.getMessageProperties().getReceivedRoutingKey();
        String body = new String(message.getBody());
        JsonNode json = objectMapper.readTree(body);

        if (routingKey.equals(eventOne)) {
            String email = json.get("email").asText();          
            handleEmployeeCreated(email);

        } else if (routingKey.equals(eventTwo)) {
            String email = json.get("email_employee").asText(); 
            handleEmployeeDeleted(email);

        } else {
            System.out.println("[WARN] routing key desconocida: " + routingKey);
        }
    }

    private void handleEmployeeCreated(String email) {
        if (userRepository.findByEmail(email).isPresent()) {
            System.out.println("[SKIP] usuario ya existe: " + email);
            return;
        }

        User user = User.builder()
                .email(email)
                .password("")
                .role("USER")
                .enabled(true)
                .build();
        userRepository.save(user);

        String resetToken = jwtUtil.generateResetToken(email);
        System.out.println("[EVENTO] usuario.creado -> " + email + " token=" + resetToken);
    }

    private void handleEmployeeDeleted(String email) {
        userRepository.findByEmail(email).ifPresent(user -> {
            user.setEnabled(false);
            userRepository.save(user);
        });
        System.out.println("[EVENTO] usuario.inhabilitado -> " + email);
    }
}