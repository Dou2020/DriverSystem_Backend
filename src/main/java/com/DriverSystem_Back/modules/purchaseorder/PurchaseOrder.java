package com.DriverSystem_Back.modules.purchaseorder;

import com.DriverSystem_Back.modules.purchaseOrderItem.PurchaseOrderItem;
import com.DriverSystem_Back.modules.user.User;
import jakarta.persistence.*;
import lombok.*;
import java.time.*;
import java.util.*;

@Entity
@Table(name = "purchase_order")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PurchaseOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String code;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "supplier_id", nullable = false)
    private User supplier;

    @Column(nullable = false)
    private String status;

    @Column(name = "ordered_at", columnDefinition = "timestamptz", insertable = false, updatable = false)
    private OffsetDateTime orderedAt;

    @Column(name = "expected_at")
    private LocalDate expectedAt;

    @Column(length = 3, nullable = false)
    private String currency = "GTQ";

    private String notes;

    @OneToMany(mappedBy = "purchaseOrder", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PurchaseOrderItem> items = new ArrayList<>();
}