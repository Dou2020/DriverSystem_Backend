package com.DriverSystem_Back.auth.role;

import com.DriverSystem_Back.exceptions.ServiceNotSaveException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
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
            throw new ServiceNotSaveException("El rol no existe!");
        Role role = optional.get();
        return new RoleResponse(role);
    }


    @Override
    public RoleResponse saveRole(RoleRequest request) {
        Optional<Role> optional = this.roleRepository.findByName(request.name());
        if (optional.isPresent()) {
            throw new ServiceNotSaveException("El rol ya existe!");
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
