package com.DriverSystem_Back.modules.Payment.dto;

import com.DriverSystem_Back.modules.Invoice.Invoice;
import com.DriverSystem_Back.modules.Invoice.dto.InvoiceRequest;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record PaymentRequest (
        @NotNull(message = "Invoice ID es obligatorio")
        InvoiceRequest invoice,
        @NotNull(message = "Method ID es obligatorio")
        Long methodId,
        @NotNull(message = "Amount es obligatorio")
        @DecimalMin(value = "0.01", message = "Amount debe ser mayor a 0")
        BigDecimal amount,
        String reference
) {

}
