package com.DriverSystem_Back.modules.user.dto;

import jakarta.validation.constraints.NotNull;

public record UserCodePassword(
        @NotNull(message = "El id es obligatorio")
        String email
) {
}
