package com.DriverSystem_Back.modules.report;

import com.DriverSystem_Back.modules.report.dto.PeriodoRequest;
import com.DriverSystem_Back.modules.report.dto.ReportWorkUserRequest;
import com.DriverSystem_Back.modules.workorder.view.WorkOrderResponde;
import com.DriverSystem_Back.modules.workorder.view.WorkOrderRespondeRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Map;

@Service

public class ReporteService {
    @Autowired
    private WorkOrderRespondeRepository workOrderRespondeRepository;
    @Autowired
    private  ReportRepository reportRepository;

     /* reporte de trabajos por perido de tiempo */
    public List<WorkOrderResponde> reportWorkPeriodo(PeriodoRequest request) {
        OffsetDateTime start = request.dateStart().atStartOfDay().atOffset(ZoneOffset.ofHours(-6));
        OffsetDateTime end = request.dateEnd().atTime(23, 59, 59).atOffset(ZoneOffset.ofHours(-6));
        return this.workOrderRespondeRepository.findByOpenedAtBetween(start,end);
    }

    /* reporte de trabajos por vehiculo */
    public List<Map<String, Object>> workOrderVehicle(String plate) {
        return this.reportRepository.getHistorialVehiculo(plate);
    }

    /*Reporte de trabajos realizados (fecha, tipo, mecánico)*/
    public List<Map<String, Object>> workOrderUser(ReportWorkUserRequest request) {
        return this.reportRepository.getTrabajosRealizados(request.dateStart(), request.dateEnd(),request.typeMantenimiento(),request.userId());
    }

    /*Uso de repuestos por período*/
    public List<Map<String, Object>> productPeridoTime(PeriodoRequest request) {
        return this.reportRepository.getUsoRepuestos(request.dateStart(), request.dateEnd());
    }

    //Reporte de repuestos mas usados por modelo
    public List<Map<String, Object>> repuestosVehiculo(String request) {
        return this.reportRepository.getRepuestosPorVehiculo(request);
    }

    // Reportes Financieros y de Facturación (Reporte de ingresos y egresos porperíodo,
    public List<Map<String, Object>> ReporteIngresosEgresos(PeriodoRequest request) {
        return this.reportRepository.getFinancialSummaryByPeriod(request.dateStart(), request.dateEnd());
    }

    //Reporte de egresos a proveedores )
    public List<Map<String, Object>> getSupplierExpensesDetail(PeriodoRequest request) {
        return this.reportRepository.getSupplierExpensesDetail(request.dateStart(), request.dateEnd());
    }

    //Historial de servicios por cliente,
    public List<Map<String, Object>> serviceCliente(Long id) {
        return this.reportRepository.getCustomerServiceHistory(id);
    }

    // Reporte de calificaciones de servicio )
    public List<Map<String, Object>> calificacionService() {
        return this.reportRepository.getServiceRatingsReport();
    }




}
