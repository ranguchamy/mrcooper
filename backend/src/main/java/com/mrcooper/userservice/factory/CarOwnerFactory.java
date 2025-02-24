package com.mrcooper.userservice.factory;

import com.mrcooper.userservice.model.CarOwner;
import com.mrcooper.userservice.model.User;
import com.mrcooper.userservice.repository.CarOwnerRepository;
import com.mrcooper.userservice.request.RegistrationRequest;
import com.mrcooper.userservice.service.role.IRoleService;
import com.mrcooper.userservice.utils.UserAttributesMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CarOwnerFactory {

    private final CarOwnerRepository carOwnerRepository;
    private final IRoleService roleService;
    private final UserAttributesMapper userAttributesMapper;

    public User createCarOwner(RegistrationRequest request) {
        CarOwner carOwner = new CarOwner();
        userAttributesMapper.setCommonAttributes(request, carOwner);

        carOwner.setRoles(roleService.setUserRole("CAR_OWNER"));
        return carOwnerRepository.save(carOwner);
    }
}
