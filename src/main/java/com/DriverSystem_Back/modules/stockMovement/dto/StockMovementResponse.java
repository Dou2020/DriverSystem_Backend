package com.DriverSystem_Back.modules.stockMovement.dto;

import lombok.*;
import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StockMovementResponse {
    private Long id;
    private Long productId;
    private Long locationId;
    private String movementType;
    private BigDecimal quantity;
    private BigDecimal unitCost;
    private String referenceType;
    private Long referenceId;
    private OffsetDateTime occurredAt;
}