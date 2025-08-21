package com.DriverSystem_Back.utils;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ExistsValueValidator.class)
@Documented
public @interface ExistsValue {
    String message() default "El valor no existe en la base de datos";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    Class<?> entityClass();
    String fieldName();
}
