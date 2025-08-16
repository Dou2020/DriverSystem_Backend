package com.DriverSystem_Back.modules.UserRole;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRoleRepository extends JpaRepository<UserRole,UserRoleId> {
    Optional<UserRole> findFirstByUserId(Long userId);
}
