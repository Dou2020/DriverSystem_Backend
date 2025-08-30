package com.DriverSystem_Back.modules.workLog.dto;

import com.DriverSystem_Back.modules.user.User;
import com.DriverSystem_Back.modules.workorder.WorkOrder;
import com.DriverSystem_Back.utils.ExistsValue;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record WorkLoggerRequest(
         Long id,
         @NotNull
         @ExistsValue(entityClass = WorkOrder.class, fieldName = "id", message = "No existe una orden con ese id")
         Long workOrderId,
         @ExistsValue(entityClass = User.class, fieldName = "id", message = "No existe una orden con ese id")
         Long autorId,
         @NotNull
         String logType,
         @NotNull
         String note,
         @NotNull
         BigDecimal hours
) {
}
