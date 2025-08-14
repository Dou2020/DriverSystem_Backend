package com.DriverSystem_Back.auth.role;

import jakarta.validation.constraints.NotBlank;

public record RoleRequest(
        @NotBlank String name,
        @NotBlank String code
) {

}

