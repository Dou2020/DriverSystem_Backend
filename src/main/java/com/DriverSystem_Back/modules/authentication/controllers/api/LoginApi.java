package com.DriverSystem_Back.modules.authentication.controllers.api;

import com.DriverSystem_Back.modules.authentication.dto.LoginDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/login")
public interface LoginApi {

    @PostMapping
    ResponseEntity<?> login(@RequestBody LoginDto loginDto);

}
