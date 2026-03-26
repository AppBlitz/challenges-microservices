package com.employee_microservice.model.dto;

import java.time.LocalDateTime;
import jakarta.validation.constraints.Email;

/**
 * Data object representing a message payload for RabbitMQ.
 * This record is used to transmit employee-related events to other microservices,
 * such as registration or update notifications.
 * * @param id           The unique identifier of the employee.
 * @param nameUser     The full name of the user or employee.
 * @param email        The contact email, validated by {@link Email}.
 * @param departmentID The unique identifier of the department assigned to the employee.
 * @param dateEnter    The timestamp representing when the employee entered the system.
 */
public record MessageRabbitMq(
        Long id, 
        String nameUser, 
        @Email String email, 
        Long departmentID,
        LocalDateTime dateEnter) {
}
