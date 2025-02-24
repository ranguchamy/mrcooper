package com.mrcooper.userservice.repository;

import com.mrcooper.userservice.model.CarOwner;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

public interface CarOwnerRepository extends JpaRepository<CarOwner, Long> {


}
