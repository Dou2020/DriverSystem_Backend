package com.DriverSystem_Back.modules.quotation.dto;

import com.DriverSystem_Back.modules.Item.response.ItemResponse;
import com.DriverSystem_Back.modules.quotation.Quotation;

import java.io.Serializable;
import java.util.List;

public record QuotationResponse(Quotation quotation, List<ItemResponse> itemResponse)  {
}
