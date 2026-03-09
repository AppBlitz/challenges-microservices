package com.employee_microservice.model.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.Email;

public record MessageRabbitMq(Long id, String nameUser, @Email String email, Long departmentID,
    LocalDateTime dateEnter) {

}
