package com.DriverSystem_Back.modules.vehicle;

import com.DriverSystem_Back.modules.vehicle.dto.VehicleRequest;
import com.DriverSystem_Back.modules.vehicle.view.VehicleResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/vehicle")
@AllArgsConstructor
public class VehicleController {
    private VehicleService vehicleService;

    @PostMapping("/")
    public ResponseEntity<VehicleResponse> save(@Valid @RequestBody VehicleRequest request) {
        VehicleResponse vehicle = this.vehicleService.saveVehicle(request);
        return ResponseEntity.ok(vehicle);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getVehicles( @PathVariable String id) {
        VehicleResponse vehicle = this.vehicleService.getVehicle(id);
        return ResponseEntity.ok(vehicle);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteVehicleRequestBody(@PathVariable Long id) {
        this.vehicleService.deleteVehicle(id);
        return ResponseEntity.ok().build();
    }




}
