package com.DriverSystem_Back.modules.product.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class ProductResponse {
    private Long id;
    private String name;
    private String brand;
    private Long categoryId;
    private String categoryName;
    private String unit;
    private boolean isService;
    private boolean taxable;
    private BigDecimal cost;
    private BigDecimal price;
    private boolean active;
}