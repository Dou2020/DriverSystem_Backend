package com.DriverSystem_Back.modules.UserRole;

import com.DriverSystem_Back.exceptions.ServiceNotSaveException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Service
@RestController
@RequestMapping("api/userRole")
public class UserRoleController {
    @Autowired
    private IUserRoleService userRoleService;


    @PutMapping("/")
    public ResponseEntity<?> updateRole(@RequestBody @Valid UserRoleRequest body) {
        try {
            UserRole newRole = this.userRoleService.updateRole(body);
            return ResponseEntity.status(HttpStatus.CREATED).body(newRole);
        } catch (ServiceNotSaveException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
