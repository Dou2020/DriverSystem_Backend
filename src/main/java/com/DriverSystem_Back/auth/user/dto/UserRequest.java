package com.DriverSystem_Back.auth.user;

import jakarta.validation.constraints.NotBlank;

public record UserRequest(
        @NotBlank Long id
) {
}
