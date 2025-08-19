package com.DriverSystem_Back.modules.vehiclemake;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VehicleMakeRepository extends JpaRepository<VehicleMake, Long> {
    Optional<VehicleMake> findByName(String name);
}
