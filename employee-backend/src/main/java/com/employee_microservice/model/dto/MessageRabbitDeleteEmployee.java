package com.employee_microservice.model.dto;

import jakarta.validation.constraints.Email;

/**
 * Data object representing a message to be sent via RabbitMQ when an employee is deleted.
 * This record facilitates asynchronous communication between microservices to ensure 
 * data consistency across the system.
 * * @param id_employee   The unique identifier of the employee being removed.
 * @param name_employee The full name of the employee for logging or notification purposes.
 * @param email_employee The contact email of the employee, validated by {@link Email}.
 */
public record MessageRabbitDeleteEmployee(
        Long id_employee, 
        String name_employee, 
        @Email String email_employee) {
}
