package com.DriverSystem_Back.modules.authentication.service;

import com.DriverSystem_Back.dto_common.ResponseHttpDto;
import com.DriverSystem_Back.modules.authentication.dto.LoginDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class LoginService {

    public ResponseHttpDto login(LoginDto loginDto){
        return null;
    }

}
