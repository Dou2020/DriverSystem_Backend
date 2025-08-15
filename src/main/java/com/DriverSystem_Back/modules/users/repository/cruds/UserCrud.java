package com.DriverSystem_Back.modules.users.repository.cruds;

import com.DriverSystem_Back.modules.users.repository.entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserCrud extends JpaRepository<Users, String> {

    @Query(value = "Select * from app_user where email = ?", nativeQuery = true)
    Optional<Users> findByEmail(String email);

}
