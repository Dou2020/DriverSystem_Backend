package com.DriverSystem_Back.modules.users.repository;

import com.DriverSystem_Back.exception.HttpException;
import com.DriverSystem_Back.modules.users.repository.cruds.UserCrud;
import com.DriverSystem_Back.modules.users.repository.entities.Users;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class UsersRepository {
    private final UserCrud usersCrud;

    public Users saveUser(Users users){
        try{
            return usersCrud.save(users);
        }catch (Exception exception){
            throw new HttpException("Error trying to save user", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public Optional<Users> findByEmail(String email){
        return usersCrud.findByEmail(email);
    }

    public Optional<Users> findById(String userId){ return usersCrud.findById(userId); }

}
