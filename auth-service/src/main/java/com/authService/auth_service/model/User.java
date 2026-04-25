package com.authService.auth_service.model;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import org.springframework.data.annotation.Id;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    private String email;   // identificador único

    private String password; // se almacenará encriptada con BCrypt

    private String role;     // ADMIN o USER

    private boolean enabled; // true si puede iniciar sesión
}

