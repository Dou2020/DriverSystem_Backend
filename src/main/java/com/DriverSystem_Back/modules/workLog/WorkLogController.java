package com.DriverSystem_Back.modules.workLog;

import com.DriverSystem_Back.modules.workLog.dto.WorkLogRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/work/log")
@AllArgsConstructor
public class WorkLogController {
    private WorkLogService workLogService;

    @GetMapping("/")
    public ResponseEntity<?> findAll() {
        return ResponseEntity.ok(this.workLogService.GetWorkLog());
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<?> findById(@PathVariable Long orderId) {
        return ResponseEntity.ok(this.workLogService.findWorkLog(orderId));
    }

    @PostMapping("/")
    public ResponseEntity<?> save(@RequestBody  @Valid  WorkLogRequest workLogRequest) {
        return ResponseEntity.ok(this.workLogService.save(workLogRequest));
    }

}

