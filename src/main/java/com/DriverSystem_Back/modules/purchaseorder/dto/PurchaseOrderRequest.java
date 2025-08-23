package com.DriverSystem_Back.modules.purchaseorder.dto;

import com.DriverSystem_Back.modules.purchaseOrderItem.dto.PurchaseOrderItemRequest;
import lombok.*;
import java.time.*;
import java.util.*;

@Data
public class PurchaseOrderRequest {
    private String code;
    private Long supplierId;
    private String status;
    private OffsetDateTime orderedAt;
    private LocalDate expectedAt;
    private String currency;
    private String notes;
    private List<PurchaseOrderItemRequest> items;
}