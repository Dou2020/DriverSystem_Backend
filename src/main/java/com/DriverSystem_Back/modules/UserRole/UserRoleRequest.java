package com.DriverSystem_Back.modules.UserRole;

import jakarta.validation.constraints.NotNull;

public record UserRoleRequest(
        @NotNull(message = "El rol es obligatorio")
        Long roleId,
        @NotNull(message = "El usuario es obligatorio")
        Long userId
) {}
