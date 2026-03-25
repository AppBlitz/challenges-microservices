package com.employee_microservice.model.dto;

import jakarta.validation.constraints.Email;

public record MessageRabbitDeleteEmployee(Long id_employee, String name_employee, @Email String email_employee) {
}
