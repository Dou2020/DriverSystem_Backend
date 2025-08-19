package com.DriverSystem_Back.modules.vehiclemake;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/vehicle/makes")
@AllArgsConstructor
public class VehicleMakeController {
    private final IVehicleMakeService vehicleMakeService;

    @PostMapping("/")
    public ResponseEntity<VehicleMake> saveVehicleMake(@RequestBody @Valid VehicleMakeRequest body){
          VehicleMake newVehicleMake = this.vehicleMakeService.save(body);
          return ResponseEntity.ok(newVehicleMake);
    }
    @GetMapping("/")
    public ResponseEntity<List<VehicleMake>> getVehicleMake(){
        return ResponseEntity.ok(this.vehicleMakeService.getAllVehicleMakes());
    }

    @GetMapping("/{id}")
    public ResponseEntity<VehicleMake> getVehicleMakeById(@PathVariable long id){
        VehicleMake service = this.vehicleMakeService.getVehicleMakeById(id);
        return  ResponseEntity.ok(service);
    }
    @PutMapping("/")
    public ResponseEntity<VehicleMake> updateVehicleMake(@RequestBody @Valid VehicleMakeRequest body){
        VehicleMake newVehicleMake = this.vehicleMakeService.update(body);
        return ResponseEntity.ok(newVehicleMake);
    }
    @DeleteMapping("/")
    public ResponseEntity<VehicleMake> deleteVehicleMake(@RequestParam long id){
        this.vehicleMakeService.delete(id);
        return  ResponseEntity.ok().build();
    }



}

