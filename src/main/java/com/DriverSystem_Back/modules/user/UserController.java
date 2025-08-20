package com.DriverSystem_Back.modules.user;

import com.DriverSystem_Back.modules.user.dto.UserActiveUser;
import com.DriverSystem_Back.modules.user.dto.UserRequest;
import com.DriverSystem_Back.modules.user.dto.UserResponse;
import com.DriverSystem_Back.modules.user.dto.UserSendEmail;
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
            UserResponse newUser = this.userService.saveUser(body);
            return ResponseEntity.ok().body(newUser);

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
            UserResponse newUser = this.userService.updateUser(request);
            return ResponseEntity.ok().body(newUser);

    }

    @PutMapping("/state/{id}")
    public ResponseEntity<?> updateIsActividad(@RequestBody @Valid UserActiveUser request) {
            UserActiveUser newUser = this.userService.updateActiveUser(request);
            return ResponseEntity.ok().body(newUser);
    }

    @PutMapping("/state/mfa/{id}")
    public ResponseEntity<?> updateSendEmail(@RequestBody @Valid UserSendEmail request) {
        UserSendEmail newUser = this.userService.updateSendEmail(request);
        return ResponseEntity.ok().body(newUser);
    }

    @PostMapping("/register")
    public ResponseEntity<?> RegisterUser(@RequestBody @Valid UserRequest body) {

    }






}
