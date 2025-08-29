package com.DriverSystem_Back.modules.supplierproduct.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class SupplierProductUpdateRequest {
    private String productName;
    private String productBrand;
    private Long productCategoryId;
    private String productUnit;
    private BigDecimal productPrice;
    private BigDecimal productCost;
    private Integer stockQuantity;
    private Integer leadTimeDays;
}
