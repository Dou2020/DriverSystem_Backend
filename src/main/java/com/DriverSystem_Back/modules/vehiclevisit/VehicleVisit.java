package com.DriverSystem_Back.modules.vehiclevisit;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "vehicle_visit")
public class VehicleVisit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="vehicle_id")
    private Long vehicleId;
    @Column(name = "customer_id")
    private  Long customerId;
    @Column(name="arrived_at")
    private OffsetDateTime arrivedAt;
    @Column(name="departed_at")
    private OffsetDateTime departureAt;
    private String notes;
    
    @Column(name = "status")
    @Builder.Default
    private String status = "NUEVA";
}
