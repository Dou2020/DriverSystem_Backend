package com.DriverSystem_Back.auth.user;

import com.DriverSystem_Back.auth.user.dto.UserRequest;
import com.DriverSystem_Back.auth.user.dto.UserResponse;

import java.util.List;

public interface IUserService {
    public UserResponse getUserById(Long id);
    public UserResponse saveUser(UserRequest user);
    public void deleteUserById(Long id);
    public UserResponse updateUser(UserRequest user);
    public List<UserResponse> getAllUsers();
}
