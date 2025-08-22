package com.DriverSystem_Back.modules.vehiclevisit.dto;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

/**
 * DTO para crear una nueva visita de vehículo (sin ID)
 */
public record VehicleVisitCreateRequest(
        @NotNull(message = "El campo vehicle id es obligatorio")
        Long vehicleId,
        @NotNull(message = "El campo customer id es obligatorio")
        Long customerId,
        LocalDateTime departedAt,
        String notes
) {
}
