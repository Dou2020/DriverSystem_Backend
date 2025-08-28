package com.DriverSystem_Back.modules.Payment.dto;

import com.DriverSystem_Back.modules.Invoice.dto.InvoiceResponse;
import com.DriverSystem_Back.modules.Payment.view.PaymentView;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

public record PaymentResponse (
        PaymentView  paymentView,
        InvoiceResponse invoice
) {
}
