package com.DriverSystem_Back.modules.quotation.dto;

import com.DriverSystem_Back.modules.Item.request.ItemRequest;
import com.DriverSystem_Back.modules.user.User;
import com.DriverSystem_Back.modules.workorder.WorkOrder;
import com.DriverSystem_Back.utils.ExistsValue;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record QuotationRequest(
        @NotNull
        @ExistsValue(entityClass = WorkOrder.class, fieldName = "id", message = "No existe una orden con ese id")
        Long workOrderId,
        @ExistsValue(entityClass = User.class, fieldName = "id", message = "No existe una empleado con ese id")
        Long approveBy,
        List<ItemRequest> item
) {
}
