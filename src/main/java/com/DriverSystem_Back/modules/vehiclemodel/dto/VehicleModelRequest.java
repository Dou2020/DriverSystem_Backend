package com.DriverSystem_Back.modules.vehiclemodel.dto;

import jakarta.validation.constraints.NotNull;

public record VehicleModelRequest(
        Long id,
        @NotNull (message = "Es obligatorio el make id")
        Long makeId,
        @NotNull (message = "Es obligatorio el campo name")
        String name
) {
}
