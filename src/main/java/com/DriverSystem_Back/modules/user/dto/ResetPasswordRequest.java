package com.DriverSystem_Back.modules.user.dto;

import jakarta.validation.constraints.NotBlank;

public record ResetPasswordRequest(
        @NotBlank(message = "El código es obligatorio")
        String code,
        @NotBlank(message = "La nueva contraseña es obligatoria")
        String newPassword
) {
}
