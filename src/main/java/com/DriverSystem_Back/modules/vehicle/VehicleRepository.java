package com.DriverSystem_Back.modules.vehicle;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.UUID;


public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
    Optional<Vehicle> findByPlate(String plate);
    Optional<Vehicle> findByVin(String vin);

    boolean existsByIdAndVin(Long id,  String vin);
    boolean existsByIdAndPlate(Long id, String plate);
}
