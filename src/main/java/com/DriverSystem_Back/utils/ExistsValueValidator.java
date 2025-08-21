package com.DriverSystem_Back.utils;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;


public class ExistsValueValidator implements ConstraintValidator<ExistsValue, Long> {

    private Class<?> entityClass;
    private String fieldName;

    @Autowired
    private ApplicationContext applicationContext;

    @Override
    public void initialize(ExistsValue constraintAnnotation) {
        this.entityClass = constraintAnnotation.entityClass();
        this.fieldName = constraintAnnotation.fieldName();
    }

    @Override
    public boolean isValid(Long value, ConstraintValidatorContext context) {
        if (value == null) return false;
        String repositoryBeanName = Character.toLowerCase(entityClass.getSimpleName().charAt(0))
                + entityClass.getSimpleName().substring(1) + "Repository";

        JpaRepository repository = (JpaRepository) applicationContext.getBean(repositoryBeanName);

        return repository.existsById(value);
    }
}
