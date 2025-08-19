package com.DriverSystem_Back.modules.user;

import com.DriverSystem_Back.exception.HttpException;
import com.DriverSystem_Back.modules.Userrole.UserRole;
import com.DriverSystem_Back.modules.Userrole.UserRoleRepository;
import com.DriverSystem_Back.modules.user.dto.UserActiveUser;
import com.DriverSystem_Back.modules.user.dto.UserRequest;
import com.DriverSystem_Back.modules.user.dto.UserResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

@Service

public class UserService implements IUserService {
    private UserRepository userRepository;
    private  ModelMapper modelMapper;
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    public UserService(UserRepository userRepository, ModelMapper modelMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;

        modelMapper.getConfiguration()
                .setFieldMatchingEnabled(true)
                .setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PRIVATE);
    }
    @Override
    public UserResponse getUserById(Long id) {
        User user = this.userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        return  new UserResponse(user);
    }


    @Override
    public UserResponse saveUser(UserRequest request) {
        User user= this.modelMapper.map(request, User.class);
        user.setIs_active(false);
        user.setCreated_at(OffsetDateTime.now());
        user.setPasswordHash(passwordEncoder.encode(request.passwordHash()));
        User saved = this.userRepository.save(user);
        UserResponse response = new UserResponse(saved);
        this.userRoleRepository.save(new UserRole(response.id(), request.role()));
        return response;
    }

    @Override
    public void deleteUserById(Long id) {

    }

    @Override
    public UserResponse updateUser(UserRequest request) {
            User existingUser = this.userRepository.findById(request.id())
                    .orElseThrow(() ->  new HttpException("Usuario no encontrado", HttpStatus.NOT_FOUND));
            existingUser.setName(request.name());
            existingUser.setEmail(request.email());
            existingUser.setUserName(request.userName());
            existingUser.setPhoneNumber(request.phoneNumber());
            existingUser.setDocNumber(request.docNumber());
            existingUser.setDocType(request.docType());

            if (request.passwordHash() != null && !request.passwordHash().isBlank()) {
                existingUser.setPasswordHash(passwordEncoder.encode(request.passwordHash()));
            }
            User saved = this.userRepository.save(existingUser);

            return new UserResponse(saved);

    }

    @Override
    public List<UserResponse> getAllUsers() {
        return this.userRepository.findAll().stream().map(UserResponse::new).toList();
    }


    @Override
    public UserActiveUser updateActiveUser(UserActiveUser user) {
        User existingUser = this.userRepository.findById(user.id())
                .orElseThrow(() ->  new  HttpException("Usuario no encontrado", HttpStatus.NOT_FOUND));
        if (existingUser.getIs_active().equals(user.state()))
            throw new HttpException("El usuario ya tiene el estado indicado!", HttpStatus.UNPROCESSABLE_ENTITY);
        existingUser.setIs_active(user.state());
        this.userRepository.save(existingUser);
        return new UserActiveUser(existingUser.getId(), existingUser.getIs_active());
    }
    public  Optional<User> validateUser(Long id){
        Optional<User> optional = this.userRepository.findById(id);
        if (optional.isEmpty())
            throw new HttpException("Usuario no encontrado!!", HttpStatus.NOT_FOUND);
        return optional;
    }

}
