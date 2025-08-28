package com.DriverSystem_Back.modules.Invoice.dto;
import com.DriverSystem_Back.modules.Invoice.Invoice;
import com.DriverSystem_Back.modules.Item.response.ItemResponse;

import java.math.BigDecimal;
import java.util.List;

public record InvoiceResponse(
        Invoice invoice,
        List<ItemResponse> item
) {
}
