package com.DriverSystem_Back.modules.workstatus;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/WorkStatus")
@AllArgsConstructor
public class WorkStatusController {
    private WorkStatusRepository workStatusRepository;

    @GetMapping("/")
    public ResponseEntity<?> getMaintenanceType(){
        return ResponseEntity.ok(workStatusRepository.findAll());
    }
}
