package com.mrcooper.userservice.service.role;

import com.mrcooper.userservice.model.Role;

import java.util.Set;

public interface IRoleService {

    Set<Role> setUserRole(String userType);
    Role getRoleByName(String roleName);

}
