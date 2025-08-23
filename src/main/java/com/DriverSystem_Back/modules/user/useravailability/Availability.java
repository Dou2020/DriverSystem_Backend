package com.DriverSystem_Back.modules.user.useravailability;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "user_availability")
public class Availability {

    @Id
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "employee")
    private String employee;

    @Column(name = "availability")
    private Boolean availability;

    @Column(name = "role_id")
    private Long roleId;

    @Column(name = "role")
    private String role;
}
