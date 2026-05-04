package com.authService.auth_service.services;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import java.util.Map;

@Service
public class AuthEventPublisher {

    private final RabbitTemplate rabbitTemplate;

    public AuthEventPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void publishUserCreated(String email, String token) {
        Map<String, String> payload = Map.of("email", email, "token", token);
        rabbitTemplate.convertAndSend("usuario.creado", payload);
    }

    public void publishUserRecovery(String email, String token) {
        Map<String, String> payload = Map.of("email", email, "token", token);
        rabbitTemplate.convertAndSend("usuario.recuperacion", payload);
    }
}
