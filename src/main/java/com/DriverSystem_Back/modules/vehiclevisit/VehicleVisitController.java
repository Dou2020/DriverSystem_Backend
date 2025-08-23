package com.DriverSystem_Back.modules.vehiclevisit;

import com.DriverSystem_Back.modules.vehicle.dto.VehicleVisitUpdateStatusRequest;
import com.DriverSystem_Back.modules.vehiclevisit.dto.VehicleVisitRequest;
import com.DriverSystem_Back.modules.vehiclevisit.dto.VehicleVisitCreateRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/vehicle/visit")
@AllArgsConstructor
@Tag(name = "Vehicle Visit Management", description = "Gestión de visitas de vehículos")
public class VehicleVisitController {

    private VehicleVisitService vehicleVisitService;

    @Operation(summary = "Obtener todas las visitas", description = "Lista todas las visitas de vehículos registradas")
    @GetMapping
    public ResponseEntity<?> getAllVehicleVisits(){
        return ResponseEntity.ok(this.vehicleVisitService.getAllVehicleVisits());
    }

    @Operation(summary = "Crear nueva visita", description = "Crear una nueva visita de vehículo")
    @PostMapping("/")
    public ResponseEntity<?> saveVehicle(@RequestBody @Valid VehicleVisitCreateRequest vehicleRequest){
        return ResponseEntity.ok(this.vehicleVisitService.saveVehicleVisit(vehicleRequest));
    }

    @PutMapping("/")
    public ResponseEntity<?> updateVehicle(@RequestBody  @Valid VehicleVisitRequest vehicleRequest){
        return ResponseEntity.ok(this.vehicleVisitService.saveVehicleVisit(vehicleRequest));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getVehicle( @PathVariable Long id){
        return ResponseEntity.ok(this.vehicleVisitService.getVehicleVisitById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteVehicle(@PathVariable Long id){
        this.vehicleVisitService.deleteVehicleVisit(id);
        return ResponseEntity.ok().build();
    }
    
    @GetMapping("/updateDepartedAt/{id}")
    public ResponseEntity<?> updateDepartedAt(@PathVariable Long id){
        this.vehicleVisitService.updataPartedAt(id);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Actualizar estado de visita", description = "Actualiza el estado de una visita (NUEVA, EN_PROCESO, COMPLETADA, CANCELADA)")
    @PatchMapping("/{id}/status")
    public ResponseEntity<?> updateVehicleVisitStatus(@PathVariable Long id, 
                                                     @RequestBody @Valid VehicleVisitUpdateStatusRequest request){
        
        VehicleVisit updatedVisit = this.vehicleVisitService.updateVisitStatus(id, request.status());
        
        // Devolver la visita actualizada (ya no se elimina en CANCELADA)
        return ResponseEntity.ok(updatedVisit);
    }
}
