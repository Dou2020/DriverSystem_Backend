package com.DriverSystem_Back.modules.supplierproduct;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

@Data
@NoArgsConstructor
 @AllArgsConstructor
public class SupplierProductId implements Serializable {
    private Long supplierId;
    private Long productId;

}
