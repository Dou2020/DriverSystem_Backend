package com.DriverSystem_Back.modules.report.dto;

import java.time.LocalDate;

public record PeriodoRequest(
        LocalDate dateStart,
        LocalDate dateEnd
        ) {

}
