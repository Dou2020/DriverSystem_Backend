package com.DriverSystem_Back.modules.uservehicle;

import com.DriverSystem_Back.modules.uservehicle.dto.UserVehicleRequest;
import com.DriverSystem_Back.modules.vehiclevisit.dto.VehicleVisitRequest;
import jakarta.validation.Valid;
import org.apache.catalina.startup.ClassLoaderFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user/vehicle")
public class UserVehiculoController {

     @Autowired
     private UserVehicleService userVehicleService;

    @PostMapping("/")
    public ResponseEntity<?> saveVehicle(@RequestBody @Valid UserVehicleRequest request){
        this.userVehicleService.saveUserVehicle(request);
        return ResponseEntity.ok().build();
    }
}
