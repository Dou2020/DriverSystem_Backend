package com.DriverSystem_Back.modules.authentication.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;


@Data
public class SessionUserCodeDto {

    private String code;
    private long userId;
    private OffsetDateTime tsExpired;

}
