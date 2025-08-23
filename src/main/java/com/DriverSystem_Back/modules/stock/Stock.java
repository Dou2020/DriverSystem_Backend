package com.DriverSystem_Back.modules.stock;

import com.DriverSystem_Back.modules.product.Product;
import com.DriverSystem_Back.modules.location.Location;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "stock")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@IdClass(StockId.class)
public class Stock {
    @Id
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @Id
    @ManyToOne
    @JoinColumn(name = "location_id")
    private Location location;

    @Column(nullable = false)
    private BigDecimal qty;

    @Column(nullable = false)
    private BigDecimal minQty;
}