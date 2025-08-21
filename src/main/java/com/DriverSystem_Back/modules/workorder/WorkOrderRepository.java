package com.DriverSystem_Back.modules.workorder;

import jakarta.persistence.Entity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface WorkOrderRepository extends JpaRepository<WorkOrder, Long> {
}
