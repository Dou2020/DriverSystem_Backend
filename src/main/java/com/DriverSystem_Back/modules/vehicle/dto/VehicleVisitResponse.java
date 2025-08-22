package com.DriverSystem_Back.modules.vehicle.dto;

import java.time.LocalDateTime;

public record VehicleVisitResponse(
        Long id,
        Long vehicleId,
        String vehiclePlate,
        String vehicleMake,
        String vehicleModel,
        Long customerId,
        String customerName,
        LocalDateTime arrivedAt,
        LocalDateTime departedAt,
        String notes,
        String status // NUEVA, EN_PROCESO, COMPLETADA, etc.
) {
    
    // Constructor sin status (para compatibilidad)
    public VehicleVisitResponse(Long id, Long vehicleId, String vehiclePlate, 
                               String vehicleMake, String vehicleModel, Long customerId, 
                               String customerName, LocalDateTime arrivedAt, 
                               LocalDateTime departedAt, String notes) {
        this(id, vehicleId, vehiclePlate, vehicleMake, vehicleModel, 
             customerId, customerName, arrivedAt, departedAt, notes, "NUEVA");
    }
}
