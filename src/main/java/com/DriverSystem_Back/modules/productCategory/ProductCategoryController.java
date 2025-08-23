package com.DriverSystem_Back.modules.productCategory;

import com.DriverSystem_Back.modules.productCategory.dto.ProductCategoryRequest;
import com.DriverSystem_Back.modules.productCategory.dto.ProductCategoryResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/ProductCategory")
public class ProductCategoryController {

    @Autowired
    private IProductCategoryService productCategoryService;

    @GetMapping("/")
    public ResponseEntity<List<ProductCategoryResponse>> getAllProductCategory(){
        List<ProductCategoryResponse> productCategory = this.productCategoryService.getAllProductCategory();
        return ResponseEntity.ok(productCategory);
    }

    @PostMapping("/")
    public ResponseEntity<?> saveProductCategory(@RequestBody @Valid ProductCategoryRequest body){
        ProductCategoryResponse newProductCategory = this.productCategoryService.saveProductCategory(body);
        return ResponseEntity.ok(newProductCategory);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductCategoryResponse> getProductCategory(@PathVariable Long id) {
        ProductCategoryResponse productCategory = this.productCategoryService.getProductCategoryId(id);
        return ResponseEntity.ok(productCategory);
    }


}
