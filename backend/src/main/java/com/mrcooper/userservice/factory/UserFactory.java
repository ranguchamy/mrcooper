package com.mrcooper.userservice.factory;

import com.mrcooper.userservice.model.User;
import com.mrcooper.userservice.request.RegistrationRequest;

public interface UserFactory {
    public User createUser(RegistrationRequest registrationRequest);
}
