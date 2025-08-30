package com.DriverSystem_Back.modules.product;

import com.DriverSystem_Back.modules.productCategory.ProductCategory;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "product")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String brand;

    @ManyToOne(cascade = CascadeType.ALL  )
    @JoinColumn(name = "category_id")
    private ProductCategory category;

    @Column(nullable = false)
    private String unit = "UN";

    @Column(name = "is_service", nullable = false)
    private boolean isService = false;

    @Column(nullable = false)
    private boolean taxable = true;

    @Column(precision = 14, scale = 4)
    private BigDecimal cost;

    @Column(precision = 14, scale = 4)
    private BigDecimal price;

    @Column(nullable = false)
    private boolean active = true;
}

