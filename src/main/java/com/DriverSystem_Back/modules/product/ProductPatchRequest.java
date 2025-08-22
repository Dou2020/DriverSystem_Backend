package com.DriverSystem_Back.modules.product;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class ProductPatchRequest {
    private String name;
    private String brand;
    private Long categoryId;
    private String unit;
    private Boolean isService;
    private Boolean taxable;
    private BigDecimal cost;
    private BigDecimal price;
    private Boolean active;
}