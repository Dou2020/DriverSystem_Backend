package com.DriverSystem_Back.modules.user;

import com.DriverSystem_Back.modules.user.dto.*;

import java.util.List;

public interface IUserService {
    public UserResponse getUserById(Long id);
    public UserResponse saveUser(UserRequest user);
    public void deleteUserById(Long id);
    public UserResponse updateUser(UserUpdateRequest user);
    public List<UserResponse> getAllUsers();
    public UserActiveUser updateActiveUser(UserActiveUser user);
    public UserSendEmail updateSendEmail(UserSendEmail user);
    public UserResetPassword updatePassword(UserResetPassword user);
    public UserSendEmail sendCodePassword(UserSendEmail user);
    public UserResponse  getUserDocType(String docNumer);
}
