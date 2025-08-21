package com.DriverSystem_Back.modules.user;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.OffsetDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "app_user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, name = "username")
    private String userName;

    @Email
    @Column(unique = true, nullable = false)
    private String email;
    @Column(name = "phone")

    private String phoneNumber;
    @Column(name = "password_hash")
    private String passwordHash;

    @Column(name = "is_active")
    private Boolean is_active;

    @Column(name = "user_type")
    private String userType;

    @Column(name = "doc_type")
    private String docType;

    @Column(name = "doc_number")
    private String docNumber;
    private String name;
    @Column(name = "created_at")
    private OffsetDateTime created_at;

    @Column(name = "usa_mfa", nullable = false)
    private Boolean usaMfa = false;
}
