package com.DriverSystem_Back.modules.auth.user;

import com.DriverSystem_Back.modules.auth.user.dto.UserRequest;
import com.DriverSystem_Back.modules.auth.user.dto.UserResponse;
import com.DriverSystem_Back.exceptions.ServiceNotSaveException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

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
        return null;
    }

    @Override
    public UserResponse saveUser(UserRequest request) {
        Optional<User> optional = this.userRepository.findByEmail(request.email());
        if (optional.isPresent())
            throw new ServiceNotSaveException("El usuario ya existe!");
        User user= this.modelMapper.map(request, User.class);
        user.setIs_active(true);  // Activo por defecto
        user.setCreated_at(OffsetDateTime.now());
        user.setPasswordHash(passwordEncoder.encode(request.passwordHash()));
        User saved = this.userRepository.save(user);
        UserResponse response = new UserResponse(saved);
        return response;
    }

    @Override
    public void deleteUserById(Long id) {

    }

    @Override
    public UserResponse updateUser(UserRequest user) {
        return null;
    }

    @Override
    public List<UserResponse> getAllUsers() {
        return List.of();
    }
}
