package com.DriverSystem_Back.modules.workAssignment;

import com.DriverSystem_Back.modules.workAssignment.dtio.WorkAssignmentRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/work/assignment")
@AllArgsConstructor
public class WorkAssignmentController {
    private WorkAssignmentService workAssignmentService;

    @GetMapping("/")
    public ResponseEntity<?> findAll() {
        return ResponseEntity.ok(workAssignmentService.findAll());
    }

    @PostMapping("/")
    public ResponseEntity<?> save(@RequestBody  @Valid  WorkAssignmentRequest request) {
        return ResponseEntity.ok(workAssignmentService.save(request));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        this.workAssignmentService.deleteById(id);
        return ResponseEntity.ok().build();
    }
    @PutMapping("/")
    public ResponseEntity<?> update(@RequestBody @Valid  WorkAssignmentRequest request) {
        return ResponseEntity.ok(workAssignmentService.update(request));
    }


}
