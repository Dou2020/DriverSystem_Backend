package com.DriverSystem_Back.modules.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface ResetTokenRepository extends JpaRepository<ResetToken, Long> {

    Optional<ResetToken> findByToken(String token);

    Optional<ResetToken> findByEmailAndUsedFalse(String email);

    @Modifying
    @Query("DELETE FROM ResetToken rt WHERE rt.expiryDate < :now")
    void deleteExpiredTokens(@Param("now") LocalDateTime now);

    @Modifying
    @Query("UPDATE ResetToken rt SET rt.used = true WHERE rt.token = :token")
    void markTokenAsUsed(@Param("token") String token);
}
