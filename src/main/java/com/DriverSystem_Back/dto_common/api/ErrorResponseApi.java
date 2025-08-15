package com.DriverSystem_Back.dto_common.api;

import java.time.LocalDateTime;

public record ErrorResponseApi(
        int status,
        String message,
        String path // Opcional: para saber qué endpoint falló
) {
}
