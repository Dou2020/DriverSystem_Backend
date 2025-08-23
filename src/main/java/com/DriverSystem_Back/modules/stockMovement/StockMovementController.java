package com.DriverSystem_Back.modules.stockMovement;

import com.DriverSystem_Back.modules.stockMovement.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/stock-movements")
@RequiredArgsConstructor
public class StockMovementController {
    private final StockMovementService service;

    @GetMapping
    public List<StockMovementResponse> getAll() {
        return service.findAll();
    }

    @PostMapping
    public StockMovementResponse create(@RequestBody StockMovementRequest request) {
        return service.save(request);
    }
}