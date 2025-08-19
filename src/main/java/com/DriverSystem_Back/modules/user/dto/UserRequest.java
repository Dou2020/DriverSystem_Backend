package com.DriverSystem_Back.modules.user.dto;

import com.DriverSystem_Back.modules.user.User;
import com.DriverSystem_Back.utils.UniqueValue;
import jakarta.validation.constraints.*;

public record UserRequest(

        Long id,
        @Email(message = "Debe ser un correo válido")
        @NotBlank(message = "El correo es obligatorio")
        @UniqueValue(fieldName = "email", entityClass = User.class, message = "Email ya registrado")
        String email,
        @NotBlank(message = "El nombre de usuario es obligatorio")
        @UniqueValue(fieldName = "userName", entityClass = User.class, message = "Username ya registrado")
        String userName,

        @NotBlank(message = "El número de documento es obligatorio")
        @UniqueValue(fieldName = "docNumber", entityClass = User.class, message = "Doc number ya registrado")
        String docNumber,

        @UniqueValue(fieldName = "phoneNumber", entityClass = User.class, message = "Numero de telefono  ya registrado")
        @NotBlank(message = "El número de teléfono es obligatorio")
        String phoneNumber,

        @NotBlank(message = "La contraseña es obligatoria")
        @Size(min = 8, message = "La contraseña debe tener al menos 8 caracteres")
        String passwordHash,

        @NotBlank(message = "El tipo de usuario es obligatorio")
        String userType,

        @NotBlank(message = "El nombre es obligatorio")
        String name,

        @NotNull(message = "El rol es obligatorio")
        Long role,

        @NotBlank(message = "El tipo de documento es obligatorio")
        String docType

) {}