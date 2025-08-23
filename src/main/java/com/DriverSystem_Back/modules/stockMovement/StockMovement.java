package com.DriverSystem_Back.modules.stockMovement;

import com.DriverSystem_Back.modules.product.Product;
import com.DriverSystem_Back.modules.location.Location;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Entity
@Table(name = "stock_movement")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StockMovement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @ManyToOne
    @JoinColumn(name = "location_id", nullable = false)
    private Location location;

    @Column(nullable = false)
    private String movementType;

    @Column(nullable = false)
    private BigDecimal quantity;

    private BigDecimal unitCost;

    private String referenceType;
    private Long referenceId;

    @Column(nullable = false)
    private OffsetDateTime occurredAt;
}