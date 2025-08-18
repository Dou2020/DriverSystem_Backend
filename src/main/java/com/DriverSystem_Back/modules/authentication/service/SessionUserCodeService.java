package com.DriverSystem_Back.modules.authentication.service;

import com.DriverSystem_Back.dto_common.ResponseHttpDto;
import com.DriverSystem_Back.exception.HttpException;
import com.DriverSystem_Back.modules.authentication.dto.CodeDto;
import com.DriverSystem_Back.modules.authentication.dto.SessionUserCodeDto;
import com.DriverSystem_Back.modules.authentication.repository.SessionUserCodeRepository;
import com.DriverSystem_Back.modules.authentication.repository.entity.MfaType;
import com.DriverSystem_Back.modules.authentication.repository.entity.SessionUserCode;
import com.DriverSystem_Back.modules.users.repository.UsersRepository;
import com.DriverSystem_Back.modules.users.repository.entities.Users;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.util.Optional;


@Slf4j
@RequiredArgsConstructor
@Service
public class SessionUserCodeService {
    private final SessionUserCodeRepository sessionUserCodeRepository;
    private final UsersRepository usersRepository;


    public void saveSessionUser(SessionUserCodeDto sessionUserCodeDto){
        Optional<Users> userOptional = usersRepository.findById(sessionUserCodeDto.getUserId());
        if(userOptional.isEmpty()){
            throw new HttpException("usuario no encontrado", HttpStatus.NOT_FOUND);
        }

        Users user = userOptional.get();
        SessionUserCode sessionUserCode = new SessionUserCode();
        sessionUserCode.setCode(sessionUserCodeDto.getCode());
        sessionUserCode.setUser(user);
        sessionUserCode.setEnabled(true);
        sessionUserCode.setMfaType(MfaType.EMAIL);
        sessionUserCode.setTsExpired(sessionUserCodeDto.getTsExpired());
        sessionUserCodeRepository.saveSessionUser(sessionUserCode);
    }

    public void updateOrSaveSessionCode(SessionUserCodeDto sessionUserCodeDto){

        Optional<Users> userOptional = usersRepository.findById(sessionUserCodeDto.getUserId());

        if(userOptional.isEmpty()){
            throw new HttpException("Usuario no encontrado", HttpStatus.NOT_FOUND);
        }

        Users userFound = userOptional.get();

        Optional<SessionUserCode> sessionUserCodeOptional = sessionUserCodeRepository.findByUser(userFound.getId());

        if(sessionUserCodeOptional.isEmpty()){
            saveSessionUser(sessionUserCodeDto);
        }else{
            SessionUserCode sessionUserCode = new SessionUserCode();
            sessionUserCode.setUser(userFound);
            sessionUserCode.setCode(sessionUserCodeDto.getCode());
            sessionUserCode.setTsExpired(sessionUserCodeDto.getTsExpired());
            sessionUserCodeRepository.UpdateSessionUserCode(sessionUserCode);
        }
    }

    public ResponseHttpDto verifySessionUserCode( CodeDto codeDto ){
        Optional<SessionUserCode> userCodeOptional = sessionUserCodeRepository.findByCode(codeDto.getCode());

        if(userCodeOptional.isEmpty()){
            throw new HttpException("El codigo es invalido",HttpStatus.NOT_FOUND);
        }

        SessionUserCode sessionUserCode = userCodeOptional.get();

        if(sessionUserCode.getTsExpired().isBefore(OffsetDateTime.now())){
            throw new HttpException("El codigo Expiró", HttpStatus.NOT_ACCEPTABLE);
        }

        return new ResponseHttpDto(HttpStatus.ACCEPTED.value(), "El codigo es valido");
    }

}
