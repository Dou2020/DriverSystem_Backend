package com.DriverSystem_Back.modules.vehicle.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record VehicleRequest(
        Long id,


        @NotNull(message = "El campo vin es obligatorio")
        String vin,

        @NotNull(message = "El campo plate es obligatorio")
        @Size(min = 4, max = 20, message = "El campo plate debe tener entre 4 y 20 caracteres")
        String plate,

        @NotNull(message = "El campo make es obligatorio")
        Long makeId,

        @NotNull(message = "El campo mode es obligatorio")
        Long modelId,

        @NotNull(message = "El campo color es obligatorio")
        String color,

        @NotNull(message = "El campo modelYear es obligatorio")
        Integer modelYear
) {

        }
