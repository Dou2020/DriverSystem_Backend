package com.DriverSystem_Back.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Configuration
@ConfigurationProperties(prefix = "spring.mail")
public class EmailProperties {

    private String username;
    private String issue;
    private String message;

}
