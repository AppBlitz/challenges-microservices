package com.authService.auth_service.dto;

import jakarta.validation.constraints.Email;
import lombok.Data;

@Data
public class RecoverPasswordRequest {
    @Email
    private String email;
}
