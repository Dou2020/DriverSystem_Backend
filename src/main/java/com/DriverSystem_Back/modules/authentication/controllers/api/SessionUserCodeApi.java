package com.DriverSystem_Back.modules.authentication.controllers.api;

import com.DriverSystem_Back.modules.authentication.dto.CodeDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/session_code")
public interface SessionUserCodeApi {

    @PostMapping
    ResponseEntity<?> verifyUserCode(@RequestBody CodeDto codeDto);
}
