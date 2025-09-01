package com.DriverSystem_Back.modules.report;

import com.DriverSystem_Back.modules.report.dto.PeriodoRequest;
import com.DriverSystem_Back.modules.report.dto.ReportWorkUserRequest;
import com.DriverSystem_Back.modules.workorder.view.WorkOrderRespondeRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/reporte")
@RestController
@AllArgsConstructor
public class ReporteController {
    private  ReporteService reporteService;

    @Operation(summary = "Obtener  los trabajos por perdio de tiempo", description = "Reporte de trabajos por período")
    @PostMapping("/work/order")
    public ResponseEntity<?> reportWorkOrderResponde(@RequestBody PeriodoRequest request) {
        return ResponseEntity.ok(this.reporteService.reportWorkPeriodo(request));
    }

    @Operation( summary = "Reporte de historial de mantenimiento por vehículo", description = "Reporte de historial de mantenimiento filtrado por la placa del vehiculo")
    @GetMapping("vehicle/{plate}")
    public ResponseEntity<?> reportWorkVehicleResponde(@PathVariable String plate) {
        return ResponseEntity.ok(this.reporteService.workOrderVehicle(plate));
    }

    @Operation( summary = "Reporte de trabajos realizados (fecha, tipo, mecánico)", description = "Reporte de trabajos realizados filtrado por  (fecha, tipo_mantenimineto = correctivo, mecánico = username)")
    @PostMapping("/work/order/date-type-user")
    public ResponseEntity<?> reportWorkOrderRespondeUser(@RequestBody ReportWorkUserRequest request) {
        return ResponseEntity.ok(this.reporteService.workOrderUser(request));
    }

    @Operation( summary = "Reportes de uso  repuestos por período)", description = "Reportes de Inventario y Repuestos (Uso de repuestos por período)")
    @PostMapping("spare/parts")
    public ResponseEntity<?>  reportSparePartes(@RequestBody PeriodoRequest request) {
        return ResponseEntity.ok(this.reporteService.productPeridoTime(request));
    }

    @Operation(summary = "Repuestos más usados por tipo de vehiculo (modelo)", description = "Retornada una lista de los repuestos utilizados por model de vehiculo = Civic")
    @GetMapping("vehicle/parts/{model}")
    public ResponseEntity<?> reportpartVehiculo(@PathVariable String model) {
        return ResponseEntity.ok(this.reporteService.repuestosVehiculo(model));
    }

    // Reportes Financieros y de Facturación (Reporte de ingresos y egresos por período, Reporte de egresos a proveedores )

    @Operation(summary = "Reporte de ingresos y egresos  por perido de tiempo")
    @PostMapping("financial/management")
    public ResponseEntity<?> reportIngresosEgressos(@RequestBody PeriodoRequest request) {
        return ResponseEntity.ok(this.reporteService.ReporteIngresosEgresos(request));
    }

    @Operation(summary = "Reporte de egresos a proveedores ")
    @PostMapping("financial/proveedor")
    public ResponseEntity<?> reportFiancialProveedor(@RequestBody PeriodoRequest request) {
        return ResponseEntity.ok(this.reporteService.getSupplierExpensesDetail(request));
    }


    //Reportes de Clientes y Atención (Historial de servicios por cliente, Reporte de calificaciones de servicio )
    @Operation(summary = "Historial de servicio por cliente ")
    @GetMapping("service/cliente/{id}")
    public ResponseEntity<?> reportFiancialProveedor(@PathVariable Long id) {
        return ResponseEntity.ok(this.reporteService.serviceCliente(id));
    }

    @Operation(summary = "Reporte de calificacion de servicio")
    @GetMapping("service/ranging")
    public ResponseEntity<?> serviceRanging() {
        return ResponseEntity.ok(this.reporteService.calificacionService());
    }








}


