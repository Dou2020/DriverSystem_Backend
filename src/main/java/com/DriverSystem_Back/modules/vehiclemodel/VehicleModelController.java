package com.DriverSystem_Back.modules.vehiclemodel;

import com.DriverSystem_Back.modules.vehiclemodel.dto.VehicleModelRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/vehicle/model")
@AllArgsConstructor
public class VehicleModelController {
    private VehicleModelService vehicleModelService;

    @PostMapping("/")
    public ResponseEntity<?> add(@RequestBody @Valid VehicleModelRequest vehicleModel){

        return ResponseEntity.ok().body(this.vehicleModelService.save(vehicleModel) );
    }

    @GetMapping("/")
    public ResponseEntity<?> getVehicleModel(){
        return ResponseEntity.ok().body(this.vehicleModelService.findAll() );
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@RequestBody @PathVariable Long id){
        return ResponseEntity.ok().body(this.vehicleModelService.findByMakeId(id) );
    }

    @PutMapping("/")
    public ResponseEntity<?> updateVehicleModel(@RequestBody @Valid VehicleModelRequest vehicleModel){
        return ResponseEntity.ok().body(this.vehicleModelService.update(vehicleModel) );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteVehicleModel(@RequestBody @PathVariable Long id){
        this.vehicleModelService.deleteVehicleModel(id);
        return ResponseEntity.ok().build();
    }


}
