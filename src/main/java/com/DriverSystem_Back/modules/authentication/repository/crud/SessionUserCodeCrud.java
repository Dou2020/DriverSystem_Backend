package com.DriverSystem_Back.modules.authentication.repository.crud;

import com.DriverSystem_Back.modules.authentication.dto.SessionUserCodeDto;
import com.DriverSystem_Back.modules.authentication.repository.entity.SessionUserCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SessionUserCodeCrud extends JpaRepository<SessionUserCode, String> {

    @Query(value = "UPDATE user_mfa (user_id, mfa_type, target, enabled) VALUES(?,?,?,?)", nativeQuery = true)
    void UpdateSessionUserCode(long userID, String mfaType, Boolean status );

    @Query(value = "select * from user_mfa where user_id = ? ;",nativeQuery = true)
    Optional<SessionUserCode> findByUser(long userId);
}
