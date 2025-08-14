package com.DriverSystem_Back.auth.role;

import java.math.BigInteger;

public record RoleResponse(Long id, String name, String code) {

    public RoleResponse(Role role) {
        this(role.getId(), role.getName(), role.getCode());
    }
}
