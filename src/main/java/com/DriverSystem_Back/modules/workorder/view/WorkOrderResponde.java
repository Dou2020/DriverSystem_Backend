package com.DriverSystem_Back.modules.workorder.view;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Entity
@Table(name = "get_work_order")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WorkOrderResponde {
    @Id
    private Long id;
    private String code;
    private String description;
    private BigDecimal estimatedHours;
    private OffsetDateTime openedAt;
    private OffsetDateTime closedAt;
    private String maintenanceType;
    private String status;
    private Long  customerId;
    private String docNumberCustomer;
    private String customer;
    private String phoneCustomer;
    private String createdBy;
    private String vin;
    private String plate;
    private String model;
    private Integer modelYear;
    private String color;
    private String make;
}
