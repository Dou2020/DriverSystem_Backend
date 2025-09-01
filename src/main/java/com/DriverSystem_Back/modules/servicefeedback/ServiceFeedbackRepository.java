package com.DriverSystem_Back.modules.servicefeedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;

public interface ServiceFeedbackRepository extends JpaRepository<ServiceFeedback, Long> {

    @Query(value = "SELECT sf.id, sf.customer_id AS user_feedback, sf.rating, sf.comment, sf.created_at, "
            + "gwo.id AS work_order_id, gwo.status AS work_order_status "
            + "FROM service_feedback sf "
            + "INNER JOIN get_work_order gwo ON gwo.id = sf.work_order_id "
            + "WHERE sf.customer_id = :customerId", nativeQuery = true)
    List<Map<String, Object>> findCustomFeedbackByCustomerId(@Param("customerId") Long customerId);

    boolean existsByWorkOrderIdAndCustomerId(Long aLong, Long aLong1);
}
