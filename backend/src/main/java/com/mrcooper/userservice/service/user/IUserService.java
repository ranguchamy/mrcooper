package com.mrcooper.userservice.service.user;

import com.mrcooper.userservice.model.User;
import com.mrcooper.userservice.request.RegistrationRequest;

public interface IUserService {
    User register(RegistrationRequest request);

}
