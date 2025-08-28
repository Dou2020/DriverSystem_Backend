package com.DriverSystem_Back.modules.Payment.view;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Entity
@Data
@AllArgsConstructor
@Table(name = "view_payment")
@NoArgsConstructor
public class PaymentView {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    private BigDecimal amount;
    private  OffsetDateTime paid_at;
    private String reference;
    private String  payment_method;
    private Long invoiceId;
}
