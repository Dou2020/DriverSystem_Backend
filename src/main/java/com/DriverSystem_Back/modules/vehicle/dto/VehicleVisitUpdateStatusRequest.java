package com.DriverSystem_Back.modules.vehicle.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record VehicleVisitUpdateStatusRequest(
        @NotBlank(message = "El estado es requerido")
        @Pattern(regexp = "NUEVA|EN_PROCESO|COMPLETADA|CANCELADA", 
                 message = "El estado debe ser: NUEVA, EN_PROCESO, COMPLETADA, o CANCELADA")
        String status
) {
}
