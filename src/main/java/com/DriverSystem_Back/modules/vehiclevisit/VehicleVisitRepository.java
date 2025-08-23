package com.DriverSystem_Back.modules.vehiclevisit;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VehicleVisitRepository extends JpaRepository<VehicleVisit, Long> {
    /**
     * Encuentra una visita activa (sin fecha de salida) para un vehículo y cliente específico
     */
    Optional<VehicleVisit> findByVehicleIdAndCustomerIdAndDepartureAtIsNull(Long vehicleId, Long customerId);
}
