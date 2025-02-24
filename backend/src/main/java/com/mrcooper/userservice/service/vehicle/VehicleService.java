package com.mrcooper.userservice.service.vehicle;

import com.mrcooper.userservice.model.CarOwner;
import com.mrcooper.userservice.model.Vehicle;
import com.mrcooper.userservice.repository.CarOwnerRepository;
import com.mrcooper.userservice.repository.VehicleRepository;
import com.mrcooper.userservice.model.User;
import com.mrcooper.userservice.repository.UserRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class VehicleService {
    private final VehicleRepository vehicleRepository;
    private final UserRepository userRepository;
    private final CarOwnerRepository carOwnerRepository;

    public VehicleService(VehicleRepository vehicleRepository, UserRepository userRepository, CarOwnerRepository carOwnerRepository) {
        this.vehicleRepository = vehicleRepository;
        this.userRepository = userRepository;
        this.carOwnerRepository = carOwnerRepository;
    }

    // Get Vehicles by Car Owner
    public List<Vehicle> getVehiclesByOwner(Long ownerId) {
        Optional<User> owner = userRepository.findById(ownerId);

        List<Vehicle> vehicles = owner.map(vehicleRepository::findByOwner).orElse(List.of());

        System.out.println(" Fetched Vehicles for Owner ID " + ownerId + ": " + vehicles.size());
        vehicles.forEach(vehicle -> System.out.println("🚗 Vehicle: " + vehicle.getCarMake() + " | ID: " + vehicle.getId()));

        return vehicles;
    }


    // Add New Vehicle
    public Vehicle addVehicle(Vehicle vehicle, Long ownerId) {
        CarOwner owner = carOwnerRepository.findById(ownerId)
                .orElseThrow(() -> new RuntimeException("Owner not found"));

        vehicle.setOwner(owner); //  Set CarOwner as the vehicle owner
        return vehicleRepository.save(vehicle);
    }


    // Get All Available Vehicles (For Car Users)
    public List<Vehicle> getAvailableVehicles() {
        List<Vehicle> availableVehicles = vehicleRepository.findByAvailabilityTrue();
        System.out.println("Available Vehicles Count: " + availableVehicles.size());
        availableVehicles.forEach(v -> System.out.println( v.getCarMake() + " | Rental: " + v.getRentalPrice()));
        return availableVehicles;
    }

}
