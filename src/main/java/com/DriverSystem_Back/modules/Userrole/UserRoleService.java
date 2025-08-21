package com.DriverSystem_Back.modules.Userrole;

import com.DriverSystem_Back.exception.HttpException;
import com.DriverSystem_Back.modules.role.Role;
import com.DriverSystem_Back.modules.role.RoleRepository;
import com.DriverSystem_Back.modules.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class  UserRoleService implements IUserRoleService {

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
                .orElseThrow(() -> new HttpException("No tiene asignado un rol", HttpStatus.NOT_FOUND));
    }

    @Override
    public UserRole save(Long userId, Long roleId) {
        Optional<Role>  optional =  this.roleRepository.findById(roleId);
        if(optional.isEmpty())
            throw new HttpException("El rol no existe", HttpStatus.NOT_FOUND);
        UserRole userRole = new UserRole(userId,roleId);
        return  userRoleRepository.save(userRole);
    }


    @Override
    public UserRole updateRole(UserRoleRequest request) {
        Optional<UserRole> optional = this.userRoleRepository.findFirstByUserId(request.userId());
        if (optional.isEmpty())
            throw new HttpException("El usuario no existe", HttpStatus.NOT_FOUND);

        UserRole existingUserRole = optional.get();


        this.userRoleRepository.delete(existingUserRole);

        UserRole newUserRole = new UserRole();
        newUserRole.setUserId(request.userId());
        newUserRole.setRoleId(request.roleId());

        return this.userRoleRepository.save(newUserRole);
    }


}
