package com.mrcooper.userservice.repository;

import com.mrcooper.userservice.model.CarOwner;
import com.mrcooper.userservice.model.CarUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarUserRepository extends JpaRepository<CarUser, Long> {


}
