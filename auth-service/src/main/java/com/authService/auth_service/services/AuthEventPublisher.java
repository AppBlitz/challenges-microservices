package com.authService.auth_service.services;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.util.Map;

@Service
public class AuthEventPublisher {

    @Value("${event_one}")
    private String event_one;

    @Value("${event_three}")
    private String event_three;

    @Value("${exchange_rabbit}")
    private String exchange;

    private final RabbitTemplate rabbitTemplate;

    public AuthEventPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void publishUserCreated(String email, String token) {
        Map<String, String> payload = Map.of("email", email, "token", token);
        rabbitTemplate.convertAndSend(exchange, event_one, payload);
    }

    public void publishUserRecovery(String email, String token) {
        Map<String, String> payload = Map.of("email", email, "token", token);
        rabbitTemplate.convertAndSend(exchange, event_three, payload);
    }
}
