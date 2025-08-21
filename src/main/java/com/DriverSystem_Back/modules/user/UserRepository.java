package com.DriverSystem_Back.modules.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    Long id(Long id);

    boolean existsByEmailAndIdNot(@Email(message = "Debe ser un correo válido") @NotBlank(message = "El correo es obligatorio") String email, Long id);

    boolean existsByUserNameAndIdNot(@NotBlank(message = "El nombre de usuario es obligatorio") String s, Long id);

    boolean existsByPhoneNumberAndIdNot(@NotBlank(message = "El número de teléfono es obligatorio") String s, Long id);
}
