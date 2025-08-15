package com.DriverSystem_Back.modules.users.services;

import com.DriverSystem_Back.exception.HttpException;
import com.DriverSystem_Back.modules.users.repository.UsersRepository;
import com.DriverSystem_Back.modules.users.repository.entities.Users;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UsersService {

    private final UsersRepository userRepository;
    private final PasswordService passwordService;

    public Users getByEmail(String email){

        Optional<Users> usersOptional = userRepository.findByEmail(email);

        if(usersOptional.isEmpty()){
            throw new HttpException("The user was not found", HttpStatus.NOT_FOUND);
        }
        return usersOptional.get();
    }

    public Boolean verifyUserPassword(String password, String passwordHashed){
        return passwordService.verifyPassword(password,passwordHashed);
    }

}
