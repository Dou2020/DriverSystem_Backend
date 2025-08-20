package com.DriverSystem_Back.modules.user.dto;

import jakarta.validation.constraints.NotNull;

public record UserResetPassword(
        @NotNull(message = "El estado es obligatorio")
        String code,
        @NotNull(message = "El estado es obligatorio")
        String newPassword

) {
}
