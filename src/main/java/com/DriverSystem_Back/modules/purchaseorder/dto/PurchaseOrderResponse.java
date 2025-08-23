package com.DriverSystem_Back.modules.purchaseorder.dto;

import com.DriverSystem_Back.modules.purchaseOrderItem.dto.PurchaseOrderItemResponse;
import lombok.*;
import java.time.*;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseOrderResponse {
    private Long id;
    private String code;
    private Long supplierId;
    private String status;
    private OffsetDateTime orderedAt;
    private LocalDate expectedAt;
    private String currency;
    private String notes;
    private List<PurchaseOrderItemResponse> items;
}