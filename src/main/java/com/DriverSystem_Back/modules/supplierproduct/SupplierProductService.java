// src/main/java/com/DriverSystem_Back/modules/supplierproduct/SupplierProductService.java
package com.DriverSystem_Back.modules.supplierproduct;

import com.DriverSystem_Back.modules.product.Product;
import com.DriverSystem_Back.modules.product.ProductRepository;
import com.DriverSystem_Back.modules.productCategory.ProductCategory;
import com.DriverSystem_Back.modules.productCategory.ProductCategoryRepository;
import com.DriverSystem_Back.modules.productCategory.ProductCategoryService;
import com.DriverSystem_Back.modules.supplierproduct.dto.SupplierProductRequest;
import com.DriverSystem_Back.modules.supplierproduct.dto.SupplierProductResponse;
import com.DriverSystem_Back.modules.supplierproduct.dto.SupplierProductUpdateRequest;
import com.DriverSystem_Back.modules.user.User;
import com.DriverSystem_Back.modules.user.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class SupplierProductService {

    @Autowired
    private SupplierProductRepository repository;
    @Autowired
    private ProductCategoryRepository productCategoryRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private UserRepository userRepository;

    public List<SupplierProduct> findAll() {
        return repository.findAll();
    }

    public Optional<SupplierProduct> findById(SupplierProductId id) {
        return repository.findById(id);
    }

    public SupplierProduct save(SupplierProduct supplierProduct) {
        log.info("Guardando SupplierProduct: Proveedor ID = {}, Producto ID = {}, Stock = {}, Lead Time = {}",
                supplierProduct.getSupplier(),
                supplierProduct.getProduct(),
                supplierProduct.getStockQuantity(),
                supplierProduct.getLeadTimeDays());

        return repository.save(supplierProduct);
    }

    public void deleteById(SupplierProductId id) {
        repository.deleteById(id);
    }

    // Actualización usando DTO
//    public Optional<SupplierProduct> update(SupplierProductRequest dto) {
//        SupplierProductId id = new SupplierProductId(dto.getSupplierId(), dto.getProductId());
//        return repository.findById(id).map(existing -> {
//            existing.setStockQuantity(dto.getStockQuantity());
//            existing.setLeadTimeDays(dto.getLeadTimeDays());
//            return repository.save(existing);
//        });
//    }

    public SupplierProductResponse update(Long supplierId, Long productId, SupplierProductUpdateRequest request) {
        SupplierProductId id = new SupplierProductId(supplierId, productId);
        SupplierProduct entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("No encontrado"));
        entity.getProduct().setName(request.getProductName());
        entity.getProduct().setBrand(request.getProductBrand());
        entity.getProduct().setPrice(request.getProductPrice());
        entity.getProduct().setCost(request.getProductCost());

        ProductCategory category = productCategoryRepository.findById(request.getProductCategoryId())
                .orElseThrow(() -> new RuntimeException("Categoría de producto no encontrada"));
        entity.getProduct().setCategory(category);

        entity.getProduct().setUnit(request.getProductUnit());
        entity.setStockQuantity(request.getStockQuantity());
        entity.setLeadTimeDays(request.getLeadTimeDays());
        return toResponseDTO(repository.save(entity));
    }

    // Métodos de mapeo DTO <-> Entity
public SupplierProduct fromRequestDTO(SupplierProductRequest dto) {
    Product product = new Product();
    // Aquí puedes setear más campos si es necesario
    product.setName(dto.getProductName());
    product.setBrand(dto.getProductBrand());
    product.setPrice(dto.getProductPrice());
    product.setCost(dto.getProductCost());
    product.setUnit(dto.getProductUnit());
    product.setActive(true);
    product.setService(false);
    product.setTaxable(true);

    ProductCategory category = productCategoryRepository.findById(dto.getProductCategoryId())
            .orElseThrow(() -> new RuntimeException("Categoría de producto no encontrada"));
    product.setCategory(category);

    // Guardar el producto antes de crear el SupplierProduct
    Product savedProduct = productRepository.save(product);

    User supplierUser = userRepository.findById(dto.getSupplierId())
            .orElseThrow(() -> new RuntimeException("Proveedor no encontrado"));
    log.info(supplierUser.getId()+"  id product: "+savedProduct.getId());

    return new SupplierProduct(
        supplierUser,
        savedProduct,
        dto.getStockQuantity(),
        dto.getLeadTimeDays()
    );
}

public SupplierProductResponse toResponseDTO(SupplierProduct entity) {
    SupplierProductResponse dto = new SupplierProductResponse();
    dto.setSupplierId(entity.getSupplier().getId());
    dto.setSupplierName(entity.getSupplier() != null ? entity.getSupplier().getName() : null);
    dto.setSupplierEmail(entity.getSupplier() != null ? entity.getSupplier().getEmail() : null);
    dto.setProductId(entity.getProduct().getId());
    if (entity.getProduct() != null) {
        dto.setProductName(entity.getProduct().getName());
        dto.setProductBrand(entity.getProduct().getBrand());
        dto.setProductPrice(entity.getProduct().getPrice());
        dto.setProductCost(entity.getProduct().getCost());
        dto.setProductUnit(entity.getProduct().getUnit());
        dto.setProductCategoryId(entity.getProduct().getCategory() != null ? entity.getProduct().getCategory().getId() : null);
        dto.setProductCategory(entity.getProduct().getCategory() != null ? entity.getProduct().getCategory().getName() : null);
        dto.setProductStock(entity.getStockQuantity());
    }
    dto.setStockQuantity(entity.getStockQuantity());
    dto.setLeadTimeDays(entity.getLeadTimeDays());
    return dto;
}

    public List<SupplierProduct> findBySupplierId(Long supplierId) {
        return repository.findBySupplierId(supplierId);
    }
}