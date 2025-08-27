package com.DriverSystem_Back.modules.Payment;
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
@Table(name = "payment")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "invoice_id", nullable = false)
    private Long invoice;

    @Column(name = "method_id", nullable = false)
    private Long method;

    @Column(nullable = false, precision = 14, scale = 4)
    private BigDecimal amount;

    @Column
    private String reference;
}
