package com.mrcooper.userservice.service.user;

import com.mrcooper.userservice.factory.UserFactory;
import com.mrcooper.userservice.model.User;
import com.mrcooper.userservice.request.RegistrationRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class UserService implements IUserService {

    private final UserFactory userFactory;

    @Override
    public User register(RegistrationRequest request) {

        return userFactory.createUser(request);
    }


}
