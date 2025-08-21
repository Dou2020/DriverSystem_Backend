package com.DriverSystem_Back.modules.uservehicle;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserVehicleRepository extends JpaRepository<UserVehicle,Long> {

    List<UserVehicle> findAllByUserId(Long id);
}
