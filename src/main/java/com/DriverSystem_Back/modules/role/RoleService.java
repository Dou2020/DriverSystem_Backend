package com.DriverSystem_Back.modules.role;

import com.DriverSystem_Back.exception.HttpException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleService  implements IRoleService{
    @Autowired
    private  RoleRepository roleRepository;

    @Override
    public RoleResponse getRoleById(Long id) {
        Optional<Role> optional = this.roleRepository.findById(id);
        if (optional.isEmpty())
            throw  new HttpException("El rol no existe!", HttpStatus.NOT_FOUND);
        Role role = optional.get();
        return new RoleResponse(role);
    }


    @Override
    public RoleResponse saveRole(RoleRequest request) {
        Optional<Role> optional = this.roleRepository.findByName(request.name());
        if (optional.isPresent()) {
            throw  new HttpException("El rol ya existe!!", HttpStatus.UNPROCESSABLE_ENTITY);
        }
        Role role = new Role();
        role.setName(request.name());
        role.setCode(request.code());

        Role saved = this.roleRepository.save(role);
        RoleResponse response = new RoleResponse(saved);
        return response;
    }



    @Override
    public List<RoleResponse> getAllRoles() {
        return this.roleRepository.findAll().stream().map(RoleResponse::new).toList();
    }
}
