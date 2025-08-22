package com.DriverSystem_Back.modules.supplierproduct.dto;

import lombok.Data;

@Data
public class SupplierProductResponse {
    private Long supplierId;
    private Long productId;
    private Integer stockQuantity;
    private Integer leadTimeDays;
}
