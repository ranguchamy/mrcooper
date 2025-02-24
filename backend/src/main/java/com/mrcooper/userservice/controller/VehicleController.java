package com.mrcooper.userservice.controller;

import com.mrcooper.userservice.model.Vehicle;
import com.mrcooper.userservice.service.vehicle.VehicleService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/vehicles")
public class VehicleController {

    private final VehicleService vehicleService;

    public VehicleController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    @GetMapping("/{ownerId}")
    public ResponseEntity<List<Vehicle>> getVehicles(@PathVariable Long ownerId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        System.out.println("🔹 Inside VehicleController - Authenticated User: " + authentication.getName());
        System.out.println("🔹 User Authorities: " + authentication.getAuthorities());

        return ResponseEntity.ok(vehicleService.getVehiclesByOwner(ownerId));
    }

    // Add a Vehicle
    @PostMapping("/{ownerId}")
    public ResponseEntity<Vehicle> addVehicle(@RequestBody Vehicle vehicle, @PathVariable Long ownerId) {
        return ResponseEntity.ok(vehicleService.addVehicle(vehicle, ownerId));
    }

    // Get Available Vehicles (For Car Users)
    @GetMapping("/available")
    public ResponseEntity<List<Vehicle>> getAvailableVehicles() {
        return ResponseEntity.ok(vehicleService.getAvailableVehicles());
    }

}
