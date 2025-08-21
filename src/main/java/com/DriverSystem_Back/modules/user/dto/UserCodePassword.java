package com.DriverSystem_Back.modules.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UserCodePassword(
        @NotNull(message = "El id es obligatorio")
        @Email
        @NotBlank(message = "Necesita un correo")
        String email
) {
}
