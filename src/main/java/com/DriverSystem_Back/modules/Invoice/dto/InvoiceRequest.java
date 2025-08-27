package com.DriverSystem_Back.modules.Invoice.dto;

import java.time.LocalDate;
import java.time.OffsetDateTime;

public record InvoiceRequest (
        String type,
        Long workOrderId,
        Long goodsReceiptId,
        Long userId,
        String notes,
        LocalDate dueDate

) {
}
