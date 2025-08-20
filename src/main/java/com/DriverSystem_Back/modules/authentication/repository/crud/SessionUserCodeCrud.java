package com.DriverSystem_Back.modules.authentication.repository.crud;

import com.DriverSystem_Back.modules.authentication.dto.SessionUserCodeDto;
import com.DriverSystem_Back.modules.authentication.repository.entity.SessionUserCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.Optional;

@Repository
public interface SessionUserCodeCrud extends JpaRepository<SessionUserCode, String> {

    String code(String code);

    @Query(value = "UPDATE user_mfa SET code=?, ts_expired=? WHERE user_id=? ", nativeQuery = true)
    void UpdateSessionUserCode(String newCode, OffsetDateTime tsExpired, long userId);

    @Query(value = "select * from user_mfa where user_id = ? ;",nativeQuery = true)
    Optional<SessionUserCode> findByUser(long userId);
}
