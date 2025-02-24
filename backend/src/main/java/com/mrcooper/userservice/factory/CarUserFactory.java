package com.mrcooper.userservice.factory;

import com.mrcooper.userservice.model.CarUser;
import com.mrcooper.userservice.model.User;
import com.mrcooper.userservice.repository.CarUserRepository;
import com.mrcooper.userservice.request.RegistrationRequest;
import com.mrcooper.userservice.service.role.IRoleService;
import com.mrcooper.userservice.utils.UserAttributesMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CarUserFactory {

    private final CarUserRepository carUserRepository;
    private final IRoleService roleService;
    private final UserAttributesMapper userAttributesMapper;

    public User createCarUser(RegistrationRequest request) {
        CarUser carUser = new CarUser();
        userAttributesMapper.setCommonAttributes(request, carUser);

        carUser.setRoles(roleService.setUserRole("CAR_USER"));

        return carUserRepository.save(carUser);
    }
}
