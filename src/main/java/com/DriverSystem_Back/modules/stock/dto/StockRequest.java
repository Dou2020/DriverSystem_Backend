package com.DriverSystem_Back.modules.stock.dto;

import lombok.*;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StockRequest {
    private Long productId;
    private Long locationId;
    private BigDecimal qty;
    private BigDecimal minQty;
}