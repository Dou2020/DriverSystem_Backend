package com.DriverSystem_Back.modules.product;

import com.DriverSystem_Back.modules.productCategory.ProductCategory;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "brand")
    private String brand;

    @ManyToOne(fetch = FetchType.LAZY) // Relación Muchos a Uno con ProductCategory
    @JoinColumn(name = "category_id", referencedColumnName = "id") // Columna de la clave foránea
    private ProductCategory category; // JPA manejará el category_id a través de este objeto

    @Column(name = "unit", nullable = false)
    private String unit = "UN"; // Se puede inicializar con el valor por defecto si no viene de la DB

    @Column(name = "is_service", nullable = false)
    private Boolean isService = false; // Valor por defecto

    @Column(name = "taxable", nullable = false)
    private Boolean taxable = true; // Valor por defecto

    @Column(name = "cost", precision = 14, scale = 4) // Mapeo de numeric(14,4)
    private BigDecimal cost;

    @Column(name = "price", precision = 14, scale = 4) // Mapeo de numeric(14,4)
    private BigDecimal price;

    @Column(name = "active", nullable = false)
    private Boolean active = true; // Valor por defecto
}
