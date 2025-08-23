package com.DriverSystem_Back.modules.stockMovement.dto;

import lombok.*;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StockMovementRequest {
    private Long productId;
    private Long locationId;
    private String movementType;
    private BigDecimal quantity;
    private BigDecimal unitCost;
    private String referenceType;
    private Long referenceId;
}