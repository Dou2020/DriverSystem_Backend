package com.DriverSystem_Back.modules.supplierproduct.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SupplierProductRequest {
    @NotNull(message = "El ID del proveedor no puede ser nulo")
    private Long supplierId;

    @NotNull(message = "El ID del producto no puede ser nulo")
    private Long productId;

    @Min(value = 0, message = "La cantidad en stock no puede ser negativa")
    private Integer stockQuantity;

    @Min(value = 0, message = "El tiempo de entrega no puede ser negativo")
    private Integer leadTimeDays;
}