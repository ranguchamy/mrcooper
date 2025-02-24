package com.mrcooper.userservice.data;

import com.mrcooper.userservice.model.Admin;
import com.mrcooper.userservice.model.CarOwner;
import com.mrcooper.userservice.model.CarUser;
import com.mrcooper.userservice.model.Role;
import com.mrcooper.userservice.repository.*;
import com.mrcooper.userservice.service.role.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Component
@Transactional
@RequiredArgsConstructor
public class DefaultDataInitializer implements ApplicationListener<ApplicationReadyEvent> {

    private final UserRepository userRepository;
    private final CarUserRepository carUserRepository;
    private final CarOwnerRepository carOwnerRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AdminRepository adminRepository;
    private final RoleService roleService;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        Set<String> defaultRoles = Set.of("ROLE_ADMIN", "ROLE_CAR_OWNER", "ROLE_CAR_USER");
        createDefaultRoleIfNotExists(defaultRoles);

        createDefaultAdminIfNotExists();
        createDefaultCarOwnerIfNotExists();
        createDefaultCarUserIfNotExists();
    }

    private void createDefaultAdminIfNotExists() {
        Role adminRole = roleService.getRoleByName("ROLE_ADMIN");
        final String defaultAdminEmail = "admin@email.com";
        if (userRepository.findByEmail(defaultAdminEmail).isPresent()) {
            return;
        }

        Admin admin = new Admin();
        admin.setFirstName("Admin");
        admin.setLastName("User");
        admin.setGender("Male");
        admin.setPhoneNumber("9876543210");
        admin.setEmail(defaultAdminEmail);
        admin.setPassword(passwordEncoder.encode("admin123"));
        admin.setUserType("ADMIN");
        admin.setRoles(new HashSet<>(Collections.singletonList(adminRole)));
        Admin savedAdmin = adminRepository.save(admin);
        savedAdmin.setEnabled(true);
        System.out.println(" Default admin user created successfully.");
    }

    private void createDefaultCarOwnerIfNotExists() {
        Role carOwnerRole = roleService.getRoleByName("ROLE_CAR_OWNER");
        final String defaultEmail = "carowner@email.com";
        if (userRepository.existsByEmail(defaultEmail)) {
            return;
        }

        CarOwner carOwner = new CarOwner();
        carOwner.setFirstName("Default");
        carOwner.setLastName("CarOwner");
        carOwner.setGender("Male");
        carOwner.setPhoneNumber("9876543212");
        carOwner.setEmail(defaultEmail);
        carOwner.setPassword(passwordEncoder.encode("carowner123"));
        carOwner.setUserType("CAR_OWNER");
        carOwner.setRoles(new HashSet<>(Collections.singletonList(carOwnerRole)));
        CarOwner savedCarOwner = carOwnerRepository.save(carOwner);
        savedCarOwner.setEnabled(true);
        System.out.println("Default car owner created successfully.");
    }

    private void createDefaultCarUserIfNotExists() {
        Role carUserRole = roleService.getRoleByName("ROLE_CAR_USER");
        final String defaultEmail = "caruser@email.com";
        if (userRepository.existsByEmail(defaultEmail)) {
            return;
        }

        CarUser carUser = new CarUser();
        carUser.setFirstName("Default");
        carUser.setLastName("CarUser");
        carUser.setGender("Male");
        carUser.setPhoneNumber("9876543211");
        carUser.setEmail(defaultEmail);
        carUser.setPassword(passwordEncoder.encode("caruser123"));
        carUser.setUserType("CAR_USER");
        carUser.setRoles(new HashSet<>(Collections.singletonList(carUserRole)));
        CarUser savedCarUser = carUserRepository.save(carUser);
        savedCarUser.setEnabled(true);
        System.out.println("Default car user created successfully.");
    }

    private void createDefaultRoleIfNotExists(Set<String> roles) {
        roles.stream()
                .filter(role -> roleRepository.findByName(role).isEmpty())
                .map(Role::new)
                .forEach(roleRepository::save);
        System.out.println(" Default roles initialized successfully.");
    }
}
