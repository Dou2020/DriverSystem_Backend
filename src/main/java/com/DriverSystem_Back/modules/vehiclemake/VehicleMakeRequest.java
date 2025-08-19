package com.DriverSystem_Back.modules.vehiclemake;

import jakarta.validation.constraints.NotBlank;

public record VehicleMakeRequest(

        Long id,
        @NotBlank(message = "El campo name es obligatorio")
        String name
) {
}
