package com.DriverSystem_Back.modules.authentication.controllers;

import com.DriverSystem_Back.modules.authentication.controllers.api.LoginApi;
import com.DriverSystem_Back.modules.authentication.dto.LoginDto;
import com.DriverSystem_Back.modules.authentication.service.LoginService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
public class LoginController implements LoginApi {

    private final LoginService loginService;

    @Override
    public ResponseEntity<?> login(LoginDto loginDto) {
        log.info("POST: /login -> request: {}",loginDto);
        return ResponseEntity.ok().body(loginService.login(loginDto));
    }


}
