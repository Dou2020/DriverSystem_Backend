package com.DriverSystem_Back.modules.user.dto;

import jakarta.validation.constraints.NotNull;

public record UserSendEmail(
        @NotNull(message = "El id es obligatorio")
        Long id,
        @NotNull(message = "El estado es obligatorio")
        Boolean state
) {

}
