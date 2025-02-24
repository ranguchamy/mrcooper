package com.mrcooper.userservice.service.role;

import com.mrcooper.userservice.exception.ResourceNotFoundException;
import com.mrcooper.userservice.model.Role;
import com.mrcooper.userservice.repository.RoleRepository;
import com.mrcooper.userservice.utils.FeedBackMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class RoleService implements IRoleService {

    private final RoleRepository roleRepository;

    @Override
    public Set<Role> setUserRole(String userType) {
        Set<Role> userRoles = new HashSet<>();
        roleRepository.findByName("ROLE_" + userType)
                .ifPresentOrElse(userRoles::add, () -> {
                    throw new ResourceNotFoundException(FeedBackMessage.ROLE_NOT_FOUND);
                });
        return userRoles;
    }

    @Override
    public Role getRoleByName(String roleName) {
        return roleRepository.findByName(roleName).orElse(null);
    }
}
