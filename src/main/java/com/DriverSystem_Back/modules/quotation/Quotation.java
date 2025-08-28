package com.DriverSystem_Back.modules.quotation;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "quotation")
public class Quotation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "code", updatable = false, insertable = false)
    private String code;
    @Column(name = "workOrderId")
    private Long workOrderId;
    private String status;
    @Column(name = "created_at", insertable = false, updatable = false)
    private OffsetDateTime createdAt;
    @Column(name="approved_at")
    private OffsetDateTime approvedAt;
    @Column(name="approved_by")
    private Long approvedBy;
    @Column  (name = "customer_id", insertable = false)
    private Long customerId;
}
