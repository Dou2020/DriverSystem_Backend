package com.DriverSystem_Back.modules.workLog;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface WorkLogRepository extends JpaRepository<WorkLog, Long> {
    List<WorkLog> findByWorkOrderId(Long workOrderId);
}
