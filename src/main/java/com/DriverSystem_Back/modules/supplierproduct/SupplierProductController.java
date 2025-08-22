package com.DriverSystem_Back.modules.supplierproduct;

import com.DriverSystem_Back.modules.supplierproduct.dto.SupplierProductRequest;
import com.DriverSystem_Back.modules.supplierproduct.dto.SupplierProductResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/supplier-products")
public class SupplierProductController {

    @Autowired
    private SupplierProductService service;

    @GetMapping
    public List<SupplierProductResponse> getAll() {
        return service.findAll().stream()
            .map(service::toResponseDTO)
            .collect(Collectors.toList());
    }

    @GetMapping("/{supplierId}/{productId}")
    public SupplierProductResponse getById(@PathVariable Long supplierId, @PathVariable Long productId) {
        return service.findById(new SupplierProductId(supplierId, productId))
            .map(service::toResponseDTO)
            .orElse(null);
    }

    @GetMapping("/supplier/{supplierId}")
    public List<SupplierProductResponse> getProductsBySupplier(@PathVariable Long supplierId) {
        return service.findBySupplierId(supplierId).stream()
                .map(service::toResponseDTO)
                .collect(Collectors.toList());
    }

    @PostMapping
    public SupplierProductResponse create(@Valid @RequestBody SupplierProductRequest dto) {
        SupplierProduct entity = service.fromRequestDTO(dto);
        return service.toResponseDTO(service.save(entity));
    }

    @PutMapping
    public SupplierProductResponse update(@Valid @RequestBody SupplierProductRequest dto) {
        SupplierProduct entity = service.fromRequestDTO(dto);
        return service.toResponseDTO(service.save(entity));
    }

    @DeleteMapping("/{supplierId}/{productId}")
    public void delete(@PathVariable Long supplierId, @PathVariable Long productId) {
        service.deleteById(new SupplierProductId(supplierId, productId));
    }
}
