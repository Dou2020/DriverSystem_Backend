package com.DriverSystem_Back.modules.workorder;

import jakarta.persistence.Entity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface WorkOrderRepository extends JpaRepository<WorkOrder, Long> {
    List<WorkOrder> findByVehicleIdAndCustomerId(Long VechileId, Long customerId);
}
