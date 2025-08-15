package com.DriverSystem_Back.modules.users.repository.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

@Getter
@Setter
@Entity
@Table(name = "app_user")
public class Users {
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

}
