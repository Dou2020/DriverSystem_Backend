package com.DriverSystem_Back.modules.auth.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record UserRequest(
        @NotBlank
        String userName,
        @Email
        @NotBlank
        String email,
        @NotBlank
        String phoneNumber,
        @NotBlank
        String passwordHash,
        @NotBlank
        String userType,
        @NotBlank
        String name,
        @NotBlank
        String role,
        @NotBlank
        String docType,
        @NotBlank
        String docNumber
) {}