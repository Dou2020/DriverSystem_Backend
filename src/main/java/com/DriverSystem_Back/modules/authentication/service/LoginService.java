package com.DriverSystem_Back.modules.authentication.service;

import com.DriverSystem_Back.dto_common.ResponseHttpDto;
import com.DriverSystem_Back.exception.HttpException;
import com.DriverSystem_Back.modules.authentication.dto.LoginDto;
import com.DriverSystem_Back.modules.authentication.dto.SessionUserCodeDto;
import com.DriverSystem_Back.modules.users.repository.entities.Users;
import com.DriverSystem_Back.modules.users.services.UsersService;
import com.DriverSystem_Back.properties.EmailProperties;
import com.DriverSystem_Back.utils.CodeUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;


@Slf4j
@RequiredArgsConstructor
@Service
public class LoginService {

    private final UsersService userService;
    private final EmailProperties emailProperties;
    @Autowired
    private final JavaMailSender mailSender;

    public void sendEmail(String destinatario, String asunto, String cuerpo) {
        try {
            SimpleMailMessage mensaje = new SimpleMailMessage();
            mensaje.setTo(destinatario);
            mensaje.setSubject(asunto);
            mensaje.setText(cuerpo);
            mensaje.setFrom(emailProperties.getUsername());

            mailSender.send(mensaje);
        } catch (Exception e) {
            throw new HttpException("No se pudo enviar el correo: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseHttpDto login(LoginDto loginDto){

        Users userFound = userService.getByEmail(loginDto.getEmail());
        if (userService.verifyUserPassword(loginDto.getPassword(), userFound.getPasswordHash())){
            String userCode = CodeUtils.generateVerificationCode();
            log.info(emailProperties.getMessage()+" "+emailProperties.getIssue()+"  "+loginDto.getEmail());
            sendEmail(loginDto.getEmail(),"Taller AyD1", "Codigo ".concat(": ").concat(userCode));
            SessionUserCodeDto sessionUserCodeDto = new SessionUserCodeDto();
            sessionUserCodeDto.setCode(userCode);
            sessionUserCodeDto.setUserId(userFound.getId());
            sessionUserCodeDto.setTsExpired(createExpirationDate());
        }else {
            throw new HttpException("Las credenciales del usuario son Incorrectas", HttpStatus.NOT_FOUND);
        }
        return new ResponseHttpDto(HttpStatus.OK.value(), "Se ha enviado un código al correo registrado");

    }

    private OffsetDateTime createExpirationDate() {
        return OffsetDateTime.now().plusMinutes(1);
    }

}
