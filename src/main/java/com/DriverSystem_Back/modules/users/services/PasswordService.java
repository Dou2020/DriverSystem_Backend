package com.DriverSystem_Back.modules.users.services;

import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

@Service
public class PasswordService {

    private PasswordEncoder passwordEncoder;

    public String hashPassword(String password){

        return passwordEncoder.encode(password);
    }

    public boolean verifyPassword(String password, String hashPassword){
        return passwordEncoder.matches(password,hashPassword);
    }
}
