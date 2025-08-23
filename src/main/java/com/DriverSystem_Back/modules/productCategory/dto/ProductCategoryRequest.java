package com.DriverSystem_Back.modules.productCategory.dto;

import jakarta.validation.constraints.NotBlank;

public record ProductCategoryRequest(
        @NotBlank String name
) {
}
