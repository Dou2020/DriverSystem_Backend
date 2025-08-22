package com.DriverSystem_Back.modules.productCategory.dto;

import com.DriverSystem_Back.modules.productCategory.ProductCategory;

public record ProductCategoryResponse(Long id, String name) {

    public ProductCategoryResponse(ProductCategory productCategory){ this(productCategory.getId(), productCategory.getName());}
}
