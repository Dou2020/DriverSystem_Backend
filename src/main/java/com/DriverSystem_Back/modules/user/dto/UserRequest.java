package com.DriverSystem_Back.modules.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record UserRequest(

        Long id,
        @NotBlank(message = "El nombre de usuario es obligatorio")
        String userName,

        @Email(message = "Debe ser un correo válido")
        @NotBlank(message = "El correo es obligatorio")
        String email,

        @NotBlank(message = "El número de teléfono es obligatorio")
        String phoneNumber,

        @NotBlank(message = "La contraseña es obligatoria")
        String passwordHash,

        @NotBlank(message = "El tipo de usuario es obligatorio")
        String userType,

        @NotBlank(message = "El nombre es obligatorio")
        String name,

        @NotNull(message = "El rol es obligatorio")
        Long role,

        @NotBlank(message = "El tipo de documento es obligatorio")
        String docType,

        @NotBlank(message = "El número de documento es obligatorio")
        String docNumber
) {}