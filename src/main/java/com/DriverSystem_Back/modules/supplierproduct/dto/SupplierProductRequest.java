package com.DriverSystem_Back.modules.supplierproduct.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class SupplierProductRequest {

    @NotNull(message = "El ID del proveedor no puede ser nulo")
    private Long supplierId;

    @NotNull(message = "El nombre del producto no puede ser nulo")
    private String productName;

    @NotNull(message = "El nombre del la marca no puede ser nulo")
    private String productBrand;

    @NotNull(message = "El nombre del producto category no puede ser nulo")
    private Long productCategoryId;

    @NotNull(message = "El nombre de la Unidad no puede ser nulo")
    private String productUnit;

    @NotNull(message = "El precio no puede ser nulo")
    @Min(value = 0, message = "La cantidad en stock no puede ser negativa")
    private BigDecimal productPrice;

    @NotNull(message = "El nombre del producto no puede ser nulo")
    @Min(value = 0, message = "La cantidad en stock no puede ser negativa")
    private BigDecimal productCost;

    @Min(value = 0, message = "La cantidad en stock no puede ser negativa")
    private Integer stockQuantity;

    @Min(value = 0, message = "El tiempo de entrega no puede ser negativo")
    private Integer leadTimeDays;

}