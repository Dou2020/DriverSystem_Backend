package com.DriverSystem_Back.modules.supplierproduct;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@IdClass(SupplierProductId.class)
@Table(name = "supplier_product")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SupplierProduct {

    @Id
    @NotNull(message = "El ID del proveedor no puede ser nulo")
    @Column(name = "supplier_id")
    private Long supplierId;

    @Id
    @NotNull(message = "El ID del producto no puede ser nulo")
    @Column(name = "product_id")
    private Long productId;

    @Min(value = 0, message = "La cantidad en stock no puede ser negativa")
    @Column(name = "stock_quantity")
    private Integer stockQuantity;

    @Min(value = 0, message = "El tiempo de entrega no puede ser negativo")
    @Column(name = "lead_time_days")
    private Integer leadTimeDays;
}