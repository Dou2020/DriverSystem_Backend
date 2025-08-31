package com.DriverSystem_Back.modules.report;

import com.DriverSystem_Back.modules.workorder.WorkOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Repository
public interface ReportRepository extends JpaRepository<WorkOrder, Long> {

        // Historial de vehículo
    //R789WER	Honda	Civic	2019	Rojo	WO-2025-0003	Corrective	Completed	ISSUE	Cliente reporta ruido en el sistema de frenos	Luis García
        @Query(value = """
        SELECT
        wl.id,
        gwo.plate,
        gwo.make,
        gwo.model,
        gwo.model_year,
        gwo.color,
        gwo.code,
        gwo.maintenance_type,
        gwo.status,
        wl.log_type,
        wl.note,
        au."name" as "employee"
        FROM work_log wl\s
        INNER JOIN get_work_order gwo ON gwo.id = wl.work_order_id
        inner join app_user au on au.id =wl.author_id
        WHERE gwo.plate = :plate
        """, nativeQuery = true)
        List<Map<String, Object>> getHistorialVehiculo(@Param("plate") String plate);


        // Reporte de trabajos realizados (fecha, tipo, mecánico)
        @Query(value = """
        SELECT 
            DATE(gwo.closed_at) as fecha,
            gwo.code,
            gwo.status,
            gwo.description,
            gwo.maintenance_type,
            au.name as employee,
           --  au.doc_number,
            au.username
        FROM work_assignment wa
        INNER JOIN app_user au ON wa.assignee_id = au.id
        INNER JOIN get_work_order gwo ON gwo.id = wa.work_order_id
        WHERE gwo.status = 'Completed'
          AND DATE(gwo.closed_at) BETWEEN :fechaInicio AND :fechaFin
          AND (:tipo IS NULL OR gwo.maintenance_type = :tipo)
          AND (:username IS NULL OR au.username = :username)
        ORDER BY DATE(gwo.closed_at)
        """, nativeQuery = true)
        List<Map<String, Object>> getTrabajosRealizados(
                @Param("fechaInicio") LocalDate fechaInicio,
                @Param("fechaFin") LocalDate fechaFin,
                @Param("tipo") String tipo,
                @Param("username") String username
        );

        // Uso de repuestos por período
        @Query(value = """
        SELECT
            q.approved_at,
            iq.product_id,
            iq.name AS product_name,
            SUM(iq.quantity) AS total_quantity
        FROM quotation q
        INNER JOIN item_quotation iq ON iq.quotation = q.id
        WHERE q.status = 'APPROVED'
          AND DATE(q.approved_at) BETWEEN :fechaInicio AND :fechaFin
        GROUP BY q.approved_at, iq.product_id, iq.name
        """, nativeQuery = true)
        List<Map<String, Object>> getUsoRepuestos(
                @Param("fechaInicio") LocalDate fechaInicio,
                @Param("fechaFin") LocalDate fechaFin
        );



    @Query(value = """
      SELECT
       iq.product_id,
       iq."name" AS product,
       v.plate,
       v.make,
       v.model,
       v.model_year,
       COUNT(*) AS veces_usado
   FROM  vehicle_model m
    inner join   getvehicle v on m.name=v.model
   INNER JOIN work_order wo ON wo.vehicle_id = v.id
   INNER JOIN quotation q ON q.work_order_id = wo.id
   INNER JOIN item_quotation iq ON iq.quotation = q.id
   WHERE m.name = :model
   GROUP BY iq.product_id, iq."name", v.plate, v.make, v.model, v.model_year
   ORDER BY veces_usado DESC;
   """, nativeQuery = true)
    List<Map<String, Object>> getRepuestosPorVehiculo(
            @Param("model") String model
    );


}

