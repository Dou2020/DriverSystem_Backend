package com.DriverSystem_Back.modules.uservehicle.dto;

import com.DriverSystem_Back.modules.user.User;
import com.DriverSystem_Back.modules.uservehicle.UserVehicle;
import com.DriverSystem_Back.modules.vehicle.Vehicle;
import com.DriverSystem_Back.utils.ExistsValue;
import jakarta.validation.constraints.NotNull;

public record UserVehicleRequest(
        Long id,
        @NotNull(message = "Es obligatorio el campo  user id")
        @ExistsValue(entityClass = User.class, fieldName = "id", message = "No existe un usuario con ese id")
         Long userId,
        @ExistsValue(entityClass = Vehicle.class, fieldName = "id", message = "No existe una vehiculo con ese id")
         @NotNull(message = "Es obligaotrio el campo vehicle id")
         Long vehicleId
) {

}
