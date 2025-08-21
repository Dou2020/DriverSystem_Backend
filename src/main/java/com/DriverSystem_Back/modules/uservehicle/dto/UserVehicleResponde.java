package com.DriverSystem_Back.modules.uservehicle.dto;

import com.DriverSystem_Back.modules.vehicle.view.VehicleResponse;

public record UserVehicleResponde(
        VehicleResponse vehicleResponse, Long id
) {
}
