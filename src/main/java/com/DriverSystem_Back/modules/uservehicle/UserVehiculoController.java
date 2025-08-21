package com.DriverSystem_Back.modules.uservehicle;

import com.DriverSystem_Back.modules.uservehicle.dto.UserVehicleRequest;
import com.DriverSystem_Back.modules.uservehicle.dto.UserVehicleResponde;
import com.DriverSystem_Back.modules.vehicle.view.VehicleResponse;
import com.DriverSystem_Back.modules.vehiclevisit.dto.VehicleVisitRequest;
import jakarta.validation.Valid;
import org.apache.catalina.startup.ClassLoaderFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user/vehicle")
public class UserVehiculoController {

     @Autowired
     private UserVehicleService userVehicleService;

    @PostMapping("/")
    public ResponseEntity<?> saveVehicle(@RequestBody @Valid UserVehicleRequest request){
        return ResponseEntity.ok(  this.userVehicleService.saveUserVehicle(request));
    }
    @GetMapping("{userId}")
    public ResponseEntity<List<UserVehicleResponde>> findAllVehicleByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(this.userVehicleService.getAllVehicles(userId));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteVehicleByUserId(@RequestParam Long id){
        this.userVehicleService.deleteUserVehicle(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/")
    public ResponseEntity<?> updateVehicle(@RequestBody @Valid UserVehicleRequest request){
        return ResponseEntity.ok(this.userVehicleService.updateUserVehicle(request));
    }
}
