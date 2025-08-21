package com.DriverSystem_Back.modules.uservehicle;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserVehicleRepository extends JpaRepository<UserVehicle,Long> {
}
