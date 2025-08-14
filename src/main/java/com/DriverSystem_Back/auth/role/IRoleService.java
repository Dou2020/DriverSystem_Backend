package com.DriverSystem_Back.auth.role;

import java.math.BigInteger;
import java.util.List;

public interface IRoleService {


    public RoleResponse getRoleById(Long id);
    public RoleResponse saveRole(RoleRequest role);
    public List<RoleResponse> getAllRoles();
}
