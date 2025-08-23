package com.DriverSystem_Back.modules.user;

import com.DriverSystem_Back.modules.user.dto.*;
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
    public ResponseEntity<?> updateUser(@RequestBody @Valid UserUpdateRequest request) {
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

    @PostMapping("/reset/password")
    public ResponseEntity<?> updatePassword(@RequestBody @Valid UserResetPassword request){
        UserResetPassword user = this.userService.updatePassword(request);
        return ResponseEntity.ok().body(user);
    }
    @PostMapping("/reset/code")
    public ResponseEntity<?> sendCodePassword(@RequestBody @Valid UserSendEmail request){
        UserSendEmail user = this.userService.sendCodePassword(request);
        return ResponseEntity.ok().body(user);
    }

    //@PostMapping("/register")
    //public ResponseEntity<?> RegisterUser(@RequestBody @Valid UserRequest body) {
    //    return null;
    //}

    @GetMapping("/docNumer/{id}")
    public ResponseEntity<?> getUserDocNumer(@PathVariable String id){
        return ResponseEntity.ok(this.userService.getUserDocType(id));
    }


    @GetMapping("/Availability")
    public ResponseEntity<?> getEmployeeAvailability(){
        return ResponseEntity.ok(this.userService.getEmployee());
    }





}
