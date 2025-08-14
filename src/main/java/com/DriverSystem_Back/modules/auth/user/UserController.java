package com.DriverSystem_Back.modules.auth.user;

import com.DriverSystem_Back.modules.auth.user.dto.UserRequest;
import com.DriverSystem_Back.modules.auth.user.dto.UserResponse;
import com.DriverSystem_Back.exceptions.ServiceNotSaveException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Service
@RestController
@RequestMapping("/user")

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

}
