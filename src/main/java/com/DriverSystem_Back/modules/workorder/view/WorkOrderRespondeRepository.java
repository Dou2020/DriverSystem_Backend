package com.DriverSystem_Back.modules.workorder.view;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.OffsetDateTime;
import java.util.List;

public interface WorkOrderRespondeRepository extends JpaRepository<WorkOrderResponde, Long> {
    List<WorkOrderResponde> findByStatus(String id);
    List<WorkOrderResponde> findByOpenedAtBetween(OffsetDateTime openedAtAfter, OffsetDateTime openedAtBefore);

}
