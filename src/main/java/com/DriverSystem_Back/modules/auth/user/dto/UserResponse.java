package com.DriverSystem_Back.modules.auth.user.dto;

import com.DriverSystem_Back.modules.auth.user.User;

import java.time.OffsetDateTime;

public record UserResponse(Long id, String name, String email, String phoneNumber, String docType, String docNumber, String role, String userType, OffsetDateTime createdAt) {
  public UserResponse(User user) {
    this(
            user.getId(),
            user.getUserName(),
            user.getEmail(),
            user.getPhoneNumber(),
            user.getDocType(),
            user.getDocNumber(),
            user.getName(),
            user.getUserType(),
            user.getCreated_at()
    );
  }
}
