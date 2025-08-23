package com.DriverSystem_Back.modules.workorder.dto;

import jakarta.validation.constraints.NotNull;

public record WorkOrderUserStateRequest(
        @NotNull
        Long userId,
        @NotNull
        Long statusId
) {
}
