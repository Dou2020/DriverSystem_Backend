package com.DriverSystem_Back.modules.product;

import com.DriverSystem_Back.modules.product.dto.ProductPatchRequest;
import com.DriverSystem_Back.modules.product.dto.ProductRequest;
import com.DriverSystem_Back.modules.product.dto.ProductResponse;
import com.DriverSystem_Back.modules.productCategory.ProductCategory;
import com.DriverSystem_Back.modules.productCategory.ProductCategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {
    private final ProductRepository repository;
    private final ProductCategoryRepository categoryRepository;

    public ProductService(ProductRepository repository, ProductCategoryRepository categoryRepository) {
        this.repository = repository;
        this.categoryRepository = categoryRepository;
    }

    public List<ProductResponse> findAll() {
        return repository.findAll().stream().map(this::toResponse).collect(Collectors.toList());
    }

    public Optional<ProductResponse> findById(Long id) {
        return repository.findById(id).map(this::toResponse);
    }

    public ProductResponse save(ProductRequest request) {
        Product product = toEntity(request);
        Product saved = repository.save(product);
        return toResponse(saved);
    }

    public ProductResponse update(Long id, ProductPatchRequest request) {
        Product product = repository.findById(id).orElseThrow();
        updateEntity(product, request);
        Product saved = repository.save(product);
        return toResponse(saved);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }


    private Product toEntity(ProductRequest request) {
        ProductCategory category = null;
        if (request.getCategoryId() != null) {
            category = categoryRepository.findById(request.getCategoryId()).orElse(null);
        }
        return new Product(
            null,
            request.getName(),
            request.getBrand(),
            category,
            request.getUnit() != null ? request.getUnit() : "UN",
            request.isService(),
            request.isTaxable(),
            request.getCost(),
            request.getPrice(),
            request.isActive()
        );
    }

    private void updateEntity(Product product, ProductPatchRequest request) {
        if (request.getName() != null) {
            product.setName(request.getName());
        }
        if (request.getBrand() != null) {
            product.setBrand(request.getBrand());
        }
        if (request.getCategoryId() != null) {
            product.setCategory(categoryRepository.findById(request.getCategoryId()).orElse(null));
        }
        if (request.getUnit() != null) {
            product.setUnit(request.getUnit());
        }
        if (request.getIsService() != null) {
            product.setService(request.getIsService());
        }
        if (request.getTaxable() != null) {
            product.setTaxable(request.getTaxable());
        }
        if (request.getCost() != null) {
            product.setCost(request.getCost());
        }
        if (request.getPrice() != null) {
            product.setPrice(request.getPrice());
        }
        if (request.getActive() != null) {
            product.setActive(request.getActive());
        }
    }

    private ProductResponse toResponse(Product product) {
        ProductResponse response = new ProductResponse();
        response.setId(product.getId());
        response.setName(product.getName());
        response.setBrand(product.getBrand());
        if (product.getCategory() != null) {
            response.setCategoryId(product.getCategory().getId());
            response.setCategoryName(product.getCategory().getName());
        }
        response.setUnit(product.getUnit());
        response.setService(product.isService());
        response.setTaxable(product.isTaxable());
        response.setCost(product.getCost());
        response.setPrice(product.getPrice());
        response.setActive(product.isActive());
        return response;
    }
}