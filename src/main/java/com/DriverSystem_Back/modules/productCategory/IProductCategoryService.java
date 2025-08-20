package com.DriverSystem_Back.modules.productCategory;

import java.util.List;

public interface IProductCategoryService {

    public ProductCategoryResponse getProductCategoryId(Long id);
    public ProductCategoryResponse saveProductCategory(ProductCategoryRequest productCategoryRequest);
    public List<ProductCategoryResponse> getAllProductCategory();

}
