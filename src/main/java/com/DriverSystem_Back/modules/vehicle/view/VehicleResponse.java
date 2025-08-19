package com.DriverSystem_Back.modules.vehicle.view;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Entity
@Table(name = "getvehicle")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class VehicleResponse {
    @Id
    private Long Id;
    private String vin;
    private String plate;
    private String color;
    private String model;
    private String make;
    private OffsetDateTime createdAt;
}
