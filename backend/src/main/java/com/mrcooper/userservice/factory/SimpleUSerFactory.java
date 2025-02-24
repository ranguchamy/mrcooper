package com.mrcooper.userservice.factory;

import com.mrcooper.userservice.exception.AlreadyExistsException;
import com.mrcooper.userservice.model.User;
import com.mrcooper.userservice.repository.UserRepository;
import com.mrcooper.userservice.request.RegistrationRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SimpleUSerFactory implements UserFactory {
    private final UserRepository userRepository;
    private final CarUserFactory carUserFactory;
    private final CarOwnerFactory carOwnerFactory;
    private final AdminFactory adminFactory;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User createUser(RegistrationRequest registrationRequest) {
        if(userRepository.existsByEmail(registrationRequest.getEmail())) {
            throw new AlreadyExistsException("Oops! "+registrationRequest.getEmail()+ " already exists!" );
        }
         registrationRequest.setPassword(passwordEncoder.encode(registrationRequest.getPassword()));
         registrationRequest.setEnabled(true);
        switch (registrationRequest.getUserType()) {
            case "CAR_USER" ->{return carUserFactory.createCarUser(registrationRequest);
            }
            case "CAR_OWNER" -> { return  carOwnerFactory.createCarOwner(registrationRequest);
            }
            case "ADMIN" -> {return adminFactory.createAdmin(registrationRequest);
            }
            default -> {
                return null;
            }
        }


    }
}
