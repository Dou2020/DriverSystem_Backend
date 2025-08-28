package com.DriverSystem_Back.modules.Invoice;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "invoice")
public class Invoice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String code;
    private String type;
    private Long quotation_id;
    private Long goodsReceiptId;
    private Long userId;

    @Column(name = "issue_date", nullable = false)
    private LocalDate issueDate;

    @Column(name = "due_date")
    private LocalDate dueDate;

    private String status;

    @Column(name = "currency", columnDefinition = "CHAR(3)")
    private String currency;

    private String notes;
    private BigDecimal total;
    @Column(name = "outstanding_balance")
    private  BigDecimal outstandingBalance;
}