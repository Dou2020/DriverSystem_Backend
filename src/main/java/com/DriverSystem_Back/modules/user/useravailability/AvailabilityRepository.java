package com.DriverSystem_Back.modules.user.useravailability;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AvailabilityRepository extends JpaRepository<Availability, Long> {
    List<Availability> findByAvailability(Boolean availability);
}
