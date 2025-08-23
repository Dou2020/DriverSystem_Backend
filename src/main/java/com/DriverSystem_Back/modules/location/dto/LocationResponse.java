package com.DriverSystem_Back.modules.location.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LocationResponse {
    private Long id;
    private String code;
    private String name;
}