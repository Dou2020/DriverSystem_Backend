package com.DriverSystem_Back.modules.supplierproduct;

import com.DriverSystem_Back.modules.product.Product;
import com.DriverSystem_Back.modules.user.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "supplier_product")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SupplierProduct {

    public SupplierProduct(Long supplierId, Long productId, Integer stockQuantity, Integer leadTimeDays) {
        this.stockQuantity = stockQuantity;
        this.leadTimeDays = leadTimeDays;
    }
    public SupplierProduct(User supplier, Product product, Integer stockQuantity, Integer leadTimeDays) {
        // Initialize the embedded ID
        this.id = new SupplierProductId(supplier.getId(), product.getId());

        // Set the entity relationships
        this.supplier = supplier;
        this.product = product;
        this.stockQuantity = stockQuantity;
        this.leadTimeDays = leadTimeDays;
    }

    @EmbeddedId
    private SupplierProductId id;


    @Min(value = 0, message = "La cantidad en stock no puede ser negativa")
    @Column(name = "stock_quantity")
    private Integer stockQuantity;

    @Min(value = 0, message = "El tiempo de entrega no puede ser negativo")
    @Column(name = "lead_time_days")
    private Integer leadTimeDays;

    // Relaciones con las entidades completas
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("supplierId")
    @JoinColumn(name = "supplier_id", referencedColumnName = "id")
    private User supplier;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("productId")
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    private Product product;
}