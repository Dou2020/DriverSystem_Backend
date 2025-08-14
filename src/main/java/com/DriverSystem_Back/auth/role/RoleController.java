package com.DriverSystem_Back.auth.role;

import com.DriverSystem_Back.exceptions.ServiceNotSaveException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.List;

@RestController
@RequestMapping("api/v1/role")
public class RoleController {
    @Autowired
    private  IRoleService roleService;

    @GetMapping("/")
    public ResponseEntity<List<RoleResponse>> getAllRoles() {
        List<RoleResponse> roles = this.roleService.getAllRoles();
        return ResponseEntity.ok(roles);
    }

    @PostMapping("/")
    public ResponseEntity<?> saveRole(@RequestBody @Valid RoleRequest body) {
        try {
            RoleResponse newRole = this.roleService.saveRole(body);
            return ResponseEntity.status(HttpStatus.CREATED).body(newRole);
        } catch (ServiceNotSaveException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @GetMapping("/{id}")
    public ResponseEntity<RoleResponse> getRoleByCode(@PathVariable Long id) {
        RoleResponse role = this.roleService.getRoleById(id);
        return ResponseEntity.ok(role);
    }
}
