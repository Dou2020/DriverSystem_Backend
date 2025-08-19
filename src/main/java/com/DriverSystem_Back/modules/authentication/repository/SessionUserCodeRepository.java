package com.DriverSystem_Back.modules.authentication.repository;

import com.DriverSystem_Back.exception.HttpException;
import com.DriverSystem_Back.modules.authentication.repository.crud.SessionUserCodeCrud;
import com.DriverSystem_Back.modules.authentication.repository.entity.SessionUserCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Repository
public class SessionUserCodeRepository {
    private final SessionUserCodeCrud sessionUserCodeCrud;

    public SessionUserCode saveSessionUser(SessionUserCode sessionUserCode){
        try{
            log.info(sessionUserCode.toString());
            return sessionUserCodeCrud.save(sessionUserCode);
        }catch (Exception ex){
            log.error("Error trying save session user crud: {}",ex);
            throw new HttpException("Error trying save session user code 22", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public void UpdateSessionUserCode(SessionUserCode sessionUserCode){
        try {
            sessionUserCodeCrud.UpdateSessionUserCode(sessionUserCode.getCode(),sessionUserCode.getTsExpired(),sessionUserCode.getUser().getId());
        }catch (Exception exception){
            log.info(exception.getMessage());
        }
    }

    public Optional<SessionUserCode> findByUser(long userId){
        return sessionUserCodeCrud.findByUser(userId);
    }

    public Optional<SessionUserCode> findByCode(String code){ return sessionUserCodeCrud.findById(code); }
}
