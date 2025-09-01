package com.DriverSystem_Back.modules.user;

import com.DriverSystem_Back.modules.user.dto.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import com.DriverSystem_Back.modules.user.PasswordResetService;

@Service
@RestController
@RequestMapping("api/user")
public class UserController {
    @Autowired
    private  IUserService userService;

    @Autowired
    private PasswordResetService passwordResetService;

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
    public ResponseEntity<?> sendCodePassword(@RequestBody @Valid UserCodePassword request){
        UserCodePassword user = this.userService.sendCodePassword(request);
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

    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestBody @Valid ForgotPasswordRequest request) {
        try {
            passwordResetService.initiatePasswordReset(request.email());
            return ResponseEntity.ok().body("Se ha enviado un email con las instrucciones para restablecer tu contraseña");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al procesar la solicitud: " + e.getMessage());
        }
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody @Valid ResetPasswordRequest request) {
        try {
            passwordResetService.resetPassword(request.code(), request.newPassword());
            return ResponseEntity.ok().body("Contraseña restablecida exitosamente");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error al restablecer la contraseña: " + e.getMessage());
        }
    }




}
