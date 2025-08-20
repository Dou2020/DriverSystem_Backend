package com.DriverSystem_Back.modules.user;

import com.DriverSystem_Back.modules.user.dto.UserActiveUser;
import com.DriverSystem_Back.modules.user.dto.UserRequest;
import com.DriverSystem_Back.modules.user.dto.UserResponse;
import com.DriverSystem_Back.modules.user.dto.UserSendEmail;

import java.util.List;

public interface IUserService {
    public UserResponse getUserById(Long id);
    public UserResponse saveUser(UserRequest user);
    public void deleteUserById(Long id);
    public UserResponse updateUser(UserRequest user);
    public List<UserResponse> getAllUsers();
    public UserActiveUser updateActiveUser(UserActiveUser user);
    public UserSendEmail updateSendEmail(UserSendEmail user);
}
