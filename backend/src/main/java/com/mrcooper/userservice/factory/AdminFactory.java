package com.mrcooper.userservice.factory;

import com.mrcooper.userservice.model.Admin;
import com.mrcooper.userservice.repository.AdminRepository;
import com.mrcooper.userservice.request.RegistrationRequest;
import com.mrcooper.userservice.service.role.IRoleService;
import com.mrcooper.userservice.utils.UserAttributesMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminFactory {
    private final AdminRepository adminRepository;
    private final IRoleService roleService;
    private final UserAttributesMapper userAttributesMapper;

    public Admin createAdmin(RegistrationRequest request) {
        Admin admin = new Admin();

        userAttributesMapper.setCommonAttributes(request, admin);

        admin.setRoles(roleService.setUserRole("ADMIN"));

        return adminRepository.save(admin);
    }
}
