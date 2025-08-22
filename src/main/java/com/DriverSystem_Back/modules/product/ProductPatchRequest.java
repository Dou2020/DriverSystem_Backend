package com.DriverSystem_Back.modules.product;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductPatchRequest {
    @Size(max = 255, message = "El nombre no puede exceder los 255 caracteres")
    private String name;

    @Size(max = 255, message = "La marca no puede exceder los 255 caracteres")
    private String brand;

    private Long categoryId;

    @Size(max = 10, message = "La unidad no puede exceder los 10 caracteres")
    private String unit;

    private Boolean isService;

    private Boolean taxable;

    @DecimalMin(value = "0.0", inclusive = true, message = "El costo debe ser mayor o igual a 0")
    private BigDecimal cost;

    @DecimalMin(value = "0.0", inclusive = true, message = "El precio debe ser mayor o igual a 0")
    private BigDecimal price;

    private Boolean active;
}