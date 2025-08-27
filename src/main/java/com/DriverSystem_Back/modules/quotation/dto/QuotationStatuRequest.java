package com.DriverSystem_Back.modules.quotation.dto;

import com.DriverSystem_Back.modules.quotation.Quotation;
import com.DriverSystem_Back.modules.user.User;
import com.DriverSystem_Back.utils.ExistsValue;
import jakarta.validation.constraints.NotNull;

public record QuotationStatuRequest(
        @NotNull
        @ExistsValue(entityClass = Quotation.class, fieldName = "id", message = "La existe registro con ese id")
        Long id,
        @NotNull
        Long statusId
) {
}
