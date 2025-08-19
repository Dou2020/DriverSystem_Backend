package com.DriverSystem_Back.modules.vehicle.view;

import com.DriverSystem_Back.modules.vehicle.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VehicleResponseRepository extends JpaRepository<VehicleResponse, Long> {
    Optional<VehicleResponse> findByPlate(String plate);
}
