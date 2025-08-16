package com.DriverSystem_Back.modules.user;

import com.DriverSystem_Back.modules.user.dto.UserActiveUser;
import com.DriverSystem_Back.modules.user.dto.UserRequest;
import com.DriverSystem_Back.modules.user.dto.UserResponse;
import com.DriverSystem_Back.exceptions.ServiceNotSaveException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Service
@RestController
@RequestMapping("api/user")

public class UserController {
    @Autowired
    private  IUserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> RegisterUser(@RequestBody @Valid UserRequest body) {
        try {
            UserResponse newUser = this.userService.saveUser(body);
            return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
        } catch (ServiceNotSaveException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @GetMapping("/")
    public ResponseEntity<List<UserResponse>> getAllRoles() {
        List<UserResponse>  users = this.userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getRoleById(@PathVariable Long id) {
        UserResponse user = this.userService.getUserById(id);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user);
    }


    @PutMapping("/")
    public ResponseEntity<?> updateUser(@RequestBody @Valid UserRequest request) {
        try {
            UserResponse newUser = this.userService.updateUser(request);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(newUser);
        } catch (ServiceNotSaveException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateIsActividad(@RequestBody @Valid UserActiveUser request) {
        try {
            UserActiveUser newUser = this.userService.updateActiveUser(request);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(newUser);
        } catch (ServiceNotSaveException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }






}
