package com.DriverSystem_Back.modules.authentication.service;

import com.DriverSystem_Back.dto_common.ResponseHttpDto;
import com.DriverSystem_Back.exception.HttpException;
import com.DriverSystem_Back.modules.authentication.dto.LoginDto;
import com.DriverSystem_Back.modules.users.repository.entities.Users;
import com.DriverSystem_Back.modules.users.services.UsersService;
import com.DriverSystem_Back.utils.CodeUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class LoginService {

    private final UsersService userService;

    public ResponseHttpDto login(LoginDto loginDto){

        Users userFound = userService.getByEmail(loginDto.getEmail());

        if (userService.verifyUserPassword(loginDto.getPassword(), userFound.getPasswordHash())){
            String userCode = CodeUtils.generateVerificationCode();
        }else {
            throw new HttpException("The user credentials are incorrect", HttpStatus.NOT_FOUND);
        }
        return new ResponseHttpDto(HttpStatus.OK.value(), "Se ha enviado un código al correo registrado");

    }

}
