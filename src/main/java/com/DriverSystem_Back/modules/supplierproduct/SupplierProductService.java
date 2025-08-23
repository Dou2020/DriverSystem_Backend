// src/main/java/com/DriverSystem_Back/modules/supplierproduct/SupplierProductService.java
package com.DriverSystem_Back.modules.supplierproduct;

import com.DriverSystem_Back.modules.supplierproduct.dto.SupplierProductRequest;
import com.DriverSystem_Back.modules.supplierproduct.dto.SupplierProductResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SupplierProductService {

    @Autowired
    private SupplierProductRepository repository;

    public List<SupplierProduct> findAll() {
        return repository.findAll();
    }

    public Optional<SupplierProduct> findById(SupplierProductId id) {
        return repository.findById(id);
    }

    public SupplierProduct save(SupplierProduct supplierProduct) {
        return repository.save(supplierProduct);
    }

    public void deleteById(SupplierProductId id) {
        repository.deleteById(id);
    }

    // Actualización usando DTO
    public Optional<SupplierProduct> update(SupplierProductRequest dto) {
        SupplierProductId id = new SupplierProductId(dto.getSupplierId(), dto.getProductId());
        return repository.findById(id).map(existing -> {
            existing.setStockQuantity(dto.getStockQuantity());
            existing.setLeadTimeDays(dto.getLeadTimeDays());
            return repository.save(existing);
        });
    }

    // Métodos de mapeo DTO <-> Entity
    public SupplierProduct fromRequestDTO(SupplierProductRequest dto) {
        return new SupplierProduct(
            dto.getSupplierId(),
            dto.getProductId(),
            dto.getStockQuantity(),
            dto.getLeadTimeDays()
        );
    }

    public SupplierProductResponse toResponseDTO(SupplierProduct entity) {
        SupplierProductResponse dto = new SupplierProductResponse();
        dto.setSupplierId(entity.getSupplierId());
        dto.setSupplierName(entity.getSupplier() != null ? entity.getSupplier().getName() : null);
        dto.setSupplierEmail(entity.getSupplier() != null ? entity.getSupplier().getEmail() : null);
        dto.setProductId(entity.getProductId());
        dto.setProductName(entity.getProduct() != null ? entity.getProduct().getName() : null);
        dto.setProductDescription(entity.getProduct() != null ? entity.getProduct().getBrand() : null);
        dto.setStockQuantity(entity.getStockQuantity());
        dto.setLeadTimeDays(entity.getLeadTimeDays());
        return dto;
    }

    public List<SupplierProduct> findBySupplierId(Long supplierId) {
        return repository.findBySupplierId(supplierId);
    }
}