package com.DriverSystem_Back.modules.product;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductRequest {

    @NotBlank(message = "El nombre del producto es obligatorio")
    private String name;

    private String brand;

    @PositiveOrZero(message = "El ID de la categoría debe ser un número positivo o cero")
    private Long categoryId;

    @NotBlank(message = "La unidad es obligatoria")
    private String unit;

    private boolean isService;

    private boolean taxable;

    @DecimalMin(value = "0.0", inclusive = true, message = "El costo debe ser mayor o igual a 0")
    @Digits(integer = 10, fraction = 4, message = "El costo debe tener un máximo de 10 dígitos enteros y 4 decimales")
    private BigDecimal cost;

    @DecimalMin(value = "0.0", inclusive = true, message = "El precio debe ser mayor o igual a 0")
    @Digits(integer = 10, fraction = 4, message = "El precio debe tener un máximo de 10 dígitos enteros y 4 decimales")
    private BigDecimal price;

    private boolean active;
}
