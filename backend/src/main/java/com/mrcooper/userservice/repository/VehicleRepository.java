package com.mrcooper.userservice.repository;

import com.mrcooper.userservice.model.Vehicle;
import com.mrcooper.userservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
    List<Vehicle> findByOwner(User owner);

    List<Vehicle> findByAvailabilityTrue();  //  Fetch only available vehicles

}
