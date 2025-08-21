package com.DriverSystem_Back.modules.maintenancetype;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/mtype")
@AllArgsConstructor
public class MtypeController {
     private MaintenanceTypeRepository mtypeRepository;

    @GetMapping("/")
    public ResponseEntity<?> getMaintenanceType(){
        return ResponseEntity.ok(mtypeRepository.findAll());
    }

}
