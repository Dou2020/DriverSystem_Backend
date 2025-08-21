package com.DriverSystem_Back.modules.user.dto;

import com.DriverSystem_Back.modules.Userrole.UserRole;
import com.DriverSystem_Back.modules.Userrole.UserRoleRepository;
import com.DriverSystem_Back.modules.Userrole.UserRoleService;
import com.DriverSystem_Back.modules.role.Role;
import com.DriverSystem_Back.modules.role.RoleRepository;
import com.DriverSystem_Back.modules.role.RoleService;
import com.DriverSystem_Back.modules.user.User;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.OffsetDateTime;


public record UserResponse(Long id, String userName, String email, String phoneNumber, String docType, String docNumber, String name, String userType, OffsetDateTime createdAt,Boolean is_active, Boolean is_active_mfa) {

  //@Autowired
  //private static final RoleService roleService;
  //private static final UserRoleService userRoleService = new UserRoleService();


  public UserResponse(User user) {

    this(
            user.getId(),
            user.getUserName(),
            user.getEmail(),
            user.getPhoneNumber(),
            user.getDocType(),
            user.getDocNumber(),
            user.getName(),
            user.getUserType(),
            user.getCreated_at(),
            user.getIs_active(),
            user.getUsaMfa()
            //roleService.getRoleById(userRoleService.findById(user.getId()).getRoleId()).name()
    );
  }
}
