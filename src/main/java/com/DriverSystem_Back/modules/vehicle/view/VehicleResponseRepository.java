package com.DriverSystem_Back.modules.vehicle.view;

import com.DriverSystem_Back.modules.vehicle.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface VehicleResponseRepository extends JpaRepository<VehicleResponse, Long> {
    Optional<VehicleResponse> findByPlate(String plate);

    @Query(value = "SELECT v.id, v.vin, v.plate, v.make_id, v.model_id, v.model_year, v.color, v.created_at, " +
           "vm.name AS make, vmo.name AS model " +
           "FROM vehicle v " +
           "LEFT JOIN vehicle_make vm ON v.make_id = vm.id " +
           "LEFT JOIN vehicle_model vmo ON v.model_id = vmo.id " +
           "WHERE NOT EXISTS (SELECT 1 FROM vehicle_user vu WHERE vu.vehicle_id = v.id)", 
           nativeQuery = true)
    List<VehicleResponse> findUnassignedVehicles();
}
