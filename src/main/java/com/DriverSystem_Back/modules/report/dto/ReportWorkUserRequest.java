package com.DriverSystem_Back.modules.report.dto;

import java.time.LocalDate;

public record ReportWorkUserRequest(
        LocalDate dateStart,
        LocalDate dateEnd,
        Long userId,
        Long  typeMantenimiento
        ) {

}
