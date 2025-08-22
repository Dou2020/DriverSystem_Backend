package com.DriverSystem_Back.modules.productCategory;

import com.DriverSystem_Back.modules.productCategory.dto.ProductCategoryRequest;
import com.DriverSystem_Back.modules.productCategory.dto.ProductCategoryResponse;

import java.util.List;

public interface IProductCategoryService {

    public ProductCategoryResponse getProductCategoryId(Long id);
    public ProductCategoryResponse saveProductCategory(ProductCategoryRequest productCategoryRequest);
    public List<ProductCategoryResponse> getAllProductCategory();

}
