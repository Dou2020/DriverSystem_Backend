package com.DriverSystem_Back.modules.authentication.service;

import com.DriverSystem_Back.modules.Userrole.IUserRoleService;
import com.DriverSystem_Back.modules.Userrole.UserRole;
import com.DriverSystem_Back.modules.role.Role;
import com.DriverSystem_Back.modules.role.RoleRepository;

import com.DriverSystem_Back.dto_common.ResponseHttpDto;
import com.DriverSystem_Back.exception.HttpException;
import com.DriverSystem_Back.modules.authentication.dto.LoginDto;
import com.DriverSystem_Back.modules.authentication.dto.SessionUserCodeDto;
import com.DriverSystem_Back.modules.authentication.repository.entity.SessionUserCode;
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
    private final SessionUserCodeService userCodeService;
    private final IUserRoleService userRoleService;
    private final RoleRepository roleRepository;
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
            Boolean usaMfa = userFound.getUsaMfa();
            String roleCode = null;

            if(!userFound.getIs_active())
                throw new HttpException("Usuario no activo", HttpStatus.NOT_FOUND);

            try {
                UserRole userRole = userRoleService.findById(userFound.getId());
                if (userRole != null) {
                    Long roleId = userRole.getRoleId();
                    Role role = roleRepository.findById(roleId).orElse(null);
                    if (role != null) {
                        roleCode = role.getCode();
                    }
                }
            } catch (Exception e) {
                // Si no tiene rol, se deja como null
            }

            if (Boolean.TRUE.equals(usaMfa)) {
                String userCode = CodeUtils.generateVerificationCode();
                log.info(emailProperties.getMessage()+" "+emailProperties.getIssue()+"  "+loginDto.getEmail());
                sendEmail(loginDto.getEmail(),"Taller AyD1", "Código ".concat(": ").concat(userCode));
                SessionUserCodeDto sessionUserCodeDto = new SessionUserCodeDto();
                sessionUserCodeDto.setCode(userCode);
                sessionUserCodeDto.setUserId(userFound.getId());
                sessionUserCodeDto.setTsExpired(createExpirationDate());
                userCodeService.updateOrSaveSessionCode(sessionUserCodeDto);
                return new ResponseHttpDto(HttpStatus.OK.value(), "Se ha enviado un código al correo registrado", roleCode, usaMfa);
            } else {
                return new ResponseHttpDto(HttpStatus.OK.value(), "Login exitoso", roleCode, usaMfa);
            }
        }else {
            throw new HttpException("Las credenciales del usuario son Incorrectas", HttpStatus.NOT_FOUND);
        }

    }

    private OffsetDateTime createExpirationDate() {
        return OffsetDateTime.now().plusMinutes(5);
    }

}
