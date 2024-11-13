package com.java.testw2m.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LoginRequest {
    @Schema(description = "Nombre de usuario")
    private String username;
    @Schema(description = "Contraseña de usuario")
    private String password;
}