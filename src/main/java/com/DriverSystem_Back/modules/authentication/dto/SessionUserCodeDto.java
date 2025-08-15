package com.DriverSystem_Back.modules.authentication.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


@Getter
@Setter
public class SessionUserCodeDto {

    private String code;
    private String userId;
    private LocalDateTime tsExpired;

}
