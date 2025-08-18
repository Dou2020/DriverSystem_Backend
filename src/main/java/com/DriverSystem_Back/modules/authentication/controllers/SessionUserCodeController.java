package com.DriverSystem_Back.modules.authentication.controllers;

import com.DriverSystem_Back.dto_common.ResponseHttpDto;
import com.DriverSystem_Back.modules.authentication.controllers.api.SessionUserCodeApi;
import com.DriverSystem_Back.modules.authentication.dto.CodeDto;
import com.DriverSystem_Back.modules.authentication.service.SessionUserCodeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class SessionUserCodeController implements SessionUserCodeApi{

    private final SessionUserCodeService sessionUserCodeService;

    @Override
    public ResponseEntity<?> verifyUserCode(CodeDto codeDto) {
        ResponseHttpDto response = sessionUserCodeService.verifySessionUserCode(codeDto);
        return ResponseEntity.accepted().body(response);
    }

}
