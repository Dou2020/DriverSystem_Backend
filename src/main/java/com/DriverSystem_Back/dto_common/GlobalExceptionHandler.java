package com.DriverSystem_Back.dto_common;

import com.DriverSystem_Back.dto_common.api.ErrorResponseApi;
import com.DriverSystem_Back.exception.HttpException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Este método se ejecutará CADA VEZ que se lance una HttpException
    // desde cualquier parte de tu aplicación que sea manejada por un controlador.
    @ExceptionHandler(HttpException.class)
    public ResponseEntity<ErrorResponseApi> handleHttpException(HttpException ex, HttpServletRequest request) {

        // 1. Creamos el objeto de respuesta de error personalizado
        ErrorResponseApi errorResponse = new ErrorResponseApi(
                ex.getHttpStatus().value(), // Obtiene el código de estado, ej: 404
                ex.getMessage(), // El mensaje que definiste: "The user was not found"
                request.getRequestURI() // La ruta que causó el error, ej: "/api/users/by-email"
        );

        // 2. Devolvemos un ResponseEntity que contiene nuestro objeto de error
        //    y el código de estado HTTP correcto. Spring lo convertirá a JSON.
        return new ResponseEntity<>(errorResponse, ex.getHttpStatus());
    }

}
