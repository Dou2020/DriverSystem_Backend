package com.DriverSystem_Back.modules.auth.role;

import java.util.List;

public interface IRoleService {


    public RoleResponse getRoleById(Long id);
    public RoleResponse saveRole(RoleRequest role);
    public List<RoleResponse> getAllRoles();
}
