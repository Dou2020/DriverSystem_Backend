package com.DriverSystem_Back.modules.workorder;

import com.DriverSystem_Back.modules.uservehicle.UserVehicle;
import com.DriverSystem_Back.modules.uservehicle.dto.UserVehicleRequest;
import com.DriverSystem_Back.modules.workorder.dto.WorkOrderRequest;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/Work/order")
@AllArgsConstructor
public class WorkOrderController {

    @Autowired
    private  WorkOrderServidor workOrderServidor;
    @PostMapping("/")
    public ResponseEntity<?> create(@RequestBody @Valid WorkOrderRequest request){
        return ResponseEntity.ok().body(workOrderServidor.save(request));
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getWorkOrder(@RequestParam Long id){
        return ResponseEntity.ok().body(workOrderServidor.findById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteWorkOrder(@PathVariable Long id){
        workOrderServidor.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/")
    public ResponseEntity<?> updateWorkOrder(@RequestBody @Valid WorkOrderRequest request){
        return ResponseEntity.ok().body(workOrderServidor.update(request));
    }
    @GetMapping("/")
    public ResponseEntity<?> getWorkOrder(){
        return ResponseEntity.ok(this.workOrderServidor.getWorkOrder());
    }

    @Operation(summary = "Obtener  las  ordenes por estado", description = "Devuelve las ordenes de trabajo según el estado selecionado")
    @GetMapping("/status/{statusId}")
    public ResponseEntity<?> getWorkOrderStatus(@PathVariable Long statusId){
        return ResponseEntity.ok(this.workOrderServidor.findBystatus(statusId));
    }
    @Operation(summary = "Obtener  las  ordenes por  vehiculo y cliente", description = "Devuelve las ordenes de trabajo según el número de placa")

    @PostMapping("/user")
    public ResponseEntity<?> getWorkOrderPlate(@RequestBody @Valid UserVehicleRequest request){
        return  ResponseEntity.ok(this.workOrderServidor.workOrderVehicle(request));
    }

}

