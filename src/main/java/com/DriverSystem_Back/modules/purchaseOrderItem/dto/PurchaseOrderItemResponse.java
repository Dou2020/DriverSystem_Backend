package com.DriverSystem_Back.modules.purchaseOrderItem.dto;

import lombok.*;
import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseOrderItemResponse {
    private Long id;
    private Long productId;
    private BigDecimal quantity;
    private BigDecimal unitCost;
    private BigDecimal discount;
    private BigDecimal taxRate;
}