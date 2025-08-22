package com.DriverSystem_Back.modules.workorder.dto;

import com.DriverSystem_Back.modules.maintenancetype.MaintenanceType;
import com.DriverSystem_Back.modules.user.User;
import com.DriverSystem_Back.modules.vehicle.Vehicle;
import com.DriverSystem_Back.modules.workstatus.WorkStatus;
import com.DriverSystem_Back.utils.ExistsValue;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

public record WorkOrderRequest(
        Long id,
      //  String code,
        @ExistsValue(entityClass = Vehicle.class, fieldName = "id", message = "vehicle no existe")
        @NotNull
        Long vehicleId,
        @NotNull
        @ExistsValue(entityClass = User.class, fieldName = "id", message = "customer no existe")
        Long customerId,
        @NotNull
         @ExistsValue(entityClass = MaintenanceType.class, fieldName = "id", message = "Maintenance no existe")
        Long typeId,
        @NotNull
        @ExistsValue(entityClass = WorkStatus.class, fieldName ="id", message = "El status no existe")
        Long statusType,
        String description,
        @NotNull
        BigDecimal estimatedHours,
        OffsetDateTime closedAt,
        @NotNull
        @ExistsValue(entityClass = User.class, fieldName = "id", message = "customer no existe")
        Long createdBy,
        Long visitId
) {
}
