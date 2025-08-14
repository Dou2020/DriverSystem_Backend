package com.DriverSystem_Back.auth.user;

import com.DriverSystem_Back.auth.user.dto.UserRequest;
import com.DriverSystem_Back.auth.user.dto.UserResponse;
import com.DriverSystem_Back.exceptions.ServiceNotSaveException;
import jakarta.validation.constraints.Null;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UserService implements IUserService {
    @Autowired
    private UserRepository userRepository;
    private final ModelMapper modelMapper;
//    private  PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
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
//        user.setPasswordHash(passwordEncoder.encode(request.passwordHash()));
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
