package com.DriverSystem_Back.modules.authentication.service;

import com.DriverSystem_Back.exception.HttpException;
import com.DriverSystem_Back.modules.authentication.dto.SessionUserCodeDto;
import com.DriverSystem_Back.modules.authentication.repository.SessionUserCodeRepository;
import com.DriverSystem_Back.modules.authentication.repository.entity.SessionUserCode;
import com.DriverSystem_Back.modules.users.repository.UsersRepository;
import com.DriverSystem_Back.modules.users.repository.entities.Users;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

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
            throw new HttpException("The user was not found", HttpStatus.NOT_FOUND);
        }

        Users user = userOptional.get();
        SessionUserCode sessionUserCode = new SessionUserCode();
        sessionUserCode.setTarget(sessionUserCodeDto.getCode());
        sessionUserCode.setUser(user);
        sessionUserCode.setEnabled(true);
        sessionUserCodeRepository.saveSession(sessionUserCode);

    }

    public void updateOrSaveSessionCode(SessionUserCodeDto sessionUserCodeDto){

        Optional<Users> userOptional = usersRepository.findById(sessionUserCodeDto.getUserId());

        if(userOptional.isEmpty()){
            throw new HttpException("The user was not found", HttpStatus.NOT_FOUND);
        }

        Users userFound = userOptional.get();

        Optional<SessionUserCode> sessionUserCodeOptional = sessionUserCodeRepository.findByUser(userFound.getId());

        if(sessionUserCodeOptional.isEmpty()){
            saveSessionUser(sessionUserCodeDto);
        }else{
            SessionUserCode sessionUserCode = new SessionUserCode();
            sessionUserCode.setUser(userFound);
            sessionUserCode.setTarget(sessionUserCodeDto.getCode());
            sessionUserCode.setTsExpired(sessionUserCodeDto.getTsExpired());
            //sessionUserCodeRepository.updateSessionUserCode(sessionUserCode);
        }
    }

}
