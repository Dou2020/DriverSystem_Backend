package com.DriverSystem_Back.modules.authentication.service;

import com.DriverSystem_Back.dto_common.ResponseHttpDto;
import com.DriverSystem_Back.exception.HttpException;
import com.DriverSystem_Back.modules.authentication.dto.LoginDto;
import com.DriverSystem_Back.modules.users.repository.entities.Users;
import com.DriverSystem_Back.modules.users.services.PasswordService;
import com.DriverSystem_Back.modules.users.services.UsersService;
import com.DriverSystem_Back.utils.CodeUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
@Slf4j
@RequiredArgsConstructor
@Service
public class LoginService {

    private final UsersService userService;

    public ResponseHttpDto login(LoginDto loginDto){

        Users userFound = userService.getByEmail(loginDto.getEmail());
        if (userService.verifyUserPassword(loginDto.getPassword(), userFound.getPasswordHash())){
            String userCode = CodeUtils.generateVerificationCode();
            log.info(userCode);
        }else {
            throw new HttpException("Las credenciales del usuario son Incorrectas", HttpStatus.NOT_FOUND);
        }
        return new ResponseHttpDto(HttpStatus.OK.value(), "Se ha enviado un código al correo registrado");

    }

}
