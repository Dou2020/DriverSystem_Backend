package com.DriverSystem_Back.modules.productCategory;

public record ProductCategoryResponse(Long id, String name) {

    public ProductCategoryResponse(ProductCategory productCategory){ this(productCategory.getId(), productCategory.getName());}
}
