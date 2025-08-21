package com.DriverSystem_Back.modules.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UserResetPassword(
        @NotNull(message = "El estado es obligatorio")
        @NotBlank
        String code,
        @NotNull(message = "El estado es obligatorio")
        @NotBlank
        String newPassword

) {
}
