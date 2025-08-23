package com.DriverSystem_Back.modules.stock;

import com.DriverSystem_Back.modules.stock.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/stocks")
@RequiredArgsConstructor
public class StockController {
    private final StockService service;

    @GetMapping
    public List<StockResponse> getAll() {
        return service.findAll();
    }

    @PostMapping
    public StockResponse create(@RequestBody StockRequest request) {
        return service.save(request);
    }
}