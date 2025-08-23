package com.DriverSystem_Back.modules.supplierproduct;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface SupplierProductRepository extends JpaRepository<SupplierProduct, SupplierProductId> {
    List<SupplierProduct> findBySupplierId(Long supplierId);
}
