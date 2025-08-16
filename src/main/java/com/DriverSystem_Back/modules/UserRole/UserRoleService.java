package com.DriverSystem_Back.modules.UserRole;

import com.DriverSystem_Back.exceptions.ServiceNotSaveException;
import com.DriverSystem_Back.modules.role.Role;
import com.DriverSystem_Back.modules.role.RoleRepository;
import com.DriverSystem_Back.modules.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserRoleService implements IUserRoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private UserRepository UserRepository;


    @Override
    public UserRole findById(Long userId) {
        return this.userRoleRepository
                .findFirstByUserId(userId)
                .orElseThrow(() -> new ServiceNotSaveException("No tiene asiganado un rol"));
    }

    @Override
    public UserRole save(Long userId, Long roleId) {
        Optional<Role>  optional =  this.roleRepository.findById(roleId);
        if(optional.isEmpty())
            throw new ServiceNotSaveException("El role no existe");
        UserRole userRole = new UserRole(userId,roleId);
        return  userRoleRepository.save(userRole);
    }


    @Override
    public UserRole updateRole(UserRoleRequest request) {
        Optional<UserRole> optional = this.userRoleRepository.findFirstByUserId(request.userId());
        if (optional.isEmpty())
            throw new ServiceNotSaveException("El usuario no existe");
        UserRole existingUserRole = optional.get();

        if (existingUserRole.getRoleId().equals(request.roleId()) ) {
            throw new ServiceNotSaveException("El usuario ya tiene el rol asignado!");
        }
        existingUserRole.setRoleId (request.roleId());
        return this.userRoleRepository.save(existingUserRole);
    }

}
