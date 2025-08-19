package com.DriverSystem_Back.modules.authentication.repository.entity;

import com.DriverSystem_Back.modules.users.repository.entities.Users;
import jakarta.persistence.Entity;
import jakarta.persistence.*;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.*;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;


@Getter
@Setter
@Table(name = "user_mfa")
@Entity
public class SessionUserCode {
    @Id
    @Column(name = "code")
    private String code;

    // --- Relaciones ---
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE) // Coincide con ON DELETE CASCADE de tu DDL
    private Users user;

    // --- Columnas ---
    @Enumerated(EnumType.STRING)
    @Column(name = "mfa_type", nullable = false)
    private MfaType mfaType;

    @Column(name = "enabled", nullable = false)
    private boolean enabled = true; // Valor por defecto inicializado en Java

    @Column(name = "ts_expired", nullable = false, columnDefinition = "TIMESTAMPTZ")
    private OffsetDateTime tsExpired;

}
