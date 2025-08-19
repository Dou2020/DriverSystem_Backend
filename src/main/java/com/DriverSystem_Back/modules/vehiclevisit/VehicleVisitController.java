package com.DriverSystem_Back.modules.vehiclevisit;


import com.DriverSystem_Back.modules.vehiclevisit.dto.VehicleVisitRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/vehicle/visit")
@AllArgsConstructor
public class VehicleVisitController {

    private VehicleVisitService vehicleVisitService;

    @PostMapping("/")
    public ResponseEntity<?> saveVehicle(@RequestBody  @Valid VehicleVisitRequest vehicleRequest){
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



}
