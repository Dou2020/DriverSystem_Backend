package com.DriverSystem_Back.modules.UserRole;

public interface IUserRoleService {
    public UserRole findById(Long userId);
    public UserRole save(Long userId, Long roleId);
    public UserRole updateRole(UserRoleRequest userRole);
}
