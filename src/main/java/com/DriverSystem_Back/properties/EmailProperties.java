package com.DriverSystem_Back.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;


@Configuration
@ConfigurationProperties(prefix = "spring.mail")
public class EmailProperties {

    private String username;
    private String issue;
    private String message;

}
