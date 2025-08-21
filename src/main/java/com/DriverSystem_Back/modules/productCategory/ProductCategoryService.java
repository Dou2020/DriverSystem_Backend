package com.DriverSystem_Back.modules.productCategory;

import com.DriverSystem_Back.exception.HttpException;
import com.DriverSystem_Back.modules.role.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductCategoryService  implements IProductCategoryService{

    @Autowired
    private ProductCategoryRepository productCategoryRepository;

    @Override
    public ProductCategoryResponse getProductCategoryId(Long id){
        Optional<ProductCategory> optional = this.productCategoryRepository.findById(id);
        if (optional.isEmpty())
            throw new HttpException("La categoria no existe!", HttpStatus.NOT_FOUND);
        ProductCategory productCategory = optional.get();

        return new ProductCategoryResponse(productCategory);
    }

    @Override
    public ProductCategoryResponse saveProductCategory(ProductCategoryRequest request) {
        Optional<ProductCategory> optional = this.productCategoryRepository.findByName(request.name());
        if (optional.isPresent())
            throw new HttpException("La Categoria ya existe!", HttpStatus.NOT_FOUND);

        ProductCategory productCategory = new ProductCategory();
        productCategory.setName(request.name());

        ProductCategory saved = this.productCategoryRepository.save(productCategory);
        return new ProductCategoryResponse(saved);
    }

    @Override
    public List<ProductCategoryResponse> getAllProductCategory() {
        return this.productCategoryRepository.findAll().stream().map(ProductCategoryResponse::new).toList();
    }


}
