package com.DriverSystem_Back.modules.maintenancetype;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MaintenanceTypeRepository extends JpaRepository<MaintenanceType, Long> {
}
