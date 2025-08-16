package com.DriverSystem_Back.modules.role;

public record RoleResponse(Long id, String name, String code) {

    public RoleResponse(Role role) {
        this(role.getId(), role.getName(), role.getCode());
    }
}
