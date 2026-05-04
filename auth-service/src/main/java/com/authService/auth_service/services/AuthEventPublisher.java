package com.authService.auth_service.services;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.util.Map;

@Service
public class AuthEventPublisher {

    @Value("${data_rabbit.event_one}")
    private String event_one;

    @Value("${data_rabbit.event_three}")
    private String event_three;

    private final RabbitTemplate rabbitTemplate;

    public AuthEventPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void publishUserCreated(String email, String token) {
        Map<String, String> payload = Map.of("email", email, "token", token);
        rabbitTemplate.convertAndSend(event_one, payload);
    }

    public void publishUserRecovery(String email, String token) {
        Map<String, String> payload = Map.of("email", email, "token", token);
        rabbitTemplate.convertAndSend(event_three, payload);
    }
}
