package com.DriverSystem_Back.modules.workorder;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "work_order")
public class WorkOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String code;
    @Column(name = "vehicle_id")
    private Long vehicleId;
    @Column(name = "customer_id")
    private Long customerId;
    @Column(name = "type_Id")
    private Long typeId;
    @Column(name = "status_id")
    private Long statusType;
    private String description;
    @Column(name = "estimated_hours", precision = 6, scale = 2)
    private BigDecimal estimatedHours;
    @Column(name = "opened_at", nullable = false, insertable = false, updatable = false)
    private OffsetDateTime openedAt;
    private OffsetDateTime closedAt;
    @Column(name = "created_by")
    private Long createdBy;

}
