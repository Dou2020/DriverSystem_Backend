package com.DriverSystem_Back.modules.workAssignment.dtio;

import com.DriverSystem_Back.modules.role.Role;
import com.DriverSystem_Back.modules.user.User;
import com.DriverSystem_Back.modules.workorder.WorkOrder;
import com.DriverSystem_Back.utils.ExistsValue;
import jakarta.validation.constraints.NotNull;

import java.time.OffsetDateTime;

public record WorkAssignmentRequest(
        Long id,
        @ExistsValue(entityClass = WorkOrder.class, fieldName = "id", message = "No existe una orden con ese id")
        @NotNull
        Long workOrderId,
        @ExistsValue(entityClass = User.class, fieldName = "id", message = "No existe un usuario con ese id")
        @NotNull
        Long assigneeId,
        @ExistsValue(entityClass = Role.class, fieldName = "id", message = "No existe un rol con ese id")
        @NotNull
        Long role,
        OffsetDateTime assignedAt,
        OffsetDateTime releasedAt
){

}
