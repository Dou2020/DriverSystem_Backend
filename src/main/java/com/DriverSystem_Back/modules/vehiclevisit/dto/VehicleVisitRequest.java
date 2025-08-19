package com.DriverSystem_Back.modules.vehiclevisit.dto;

import jakarta.validation.constraints.NotNull;

import java.time.OffsetDateTime;

public record VehicleVisitRequest(
        Long id,
        @NotNull(message = "El campo vehicle id es obligatorio")
        Long vehicleId,
        @NotNull(message = "El campo customer id es obligatorio")
        Long customerId,
        OffsetDateTime departedAt,
        String notes
) {

}
