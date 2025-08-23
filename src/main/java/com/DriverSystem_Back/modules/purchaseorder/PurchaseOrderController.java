package com.DriverSystem_Back.modules.purchaseorder;

import com.DriverSystem_Back.modules.purchaseorder.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/purchase-orders")
@RequiredArgsConstructor
public class PurchaseOrderController {
    private final PurchaseOrderService service;

    @PostMapping
    public PurchaseOrder create(@RequestBody PurchaseOrderRequest request) {
        return service.create(request);
    }

    @GetMapping
    public List<PurchaseOrderResponse> getAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public PurchaseOrderResponse getById(@PathVariable Long id) {
        return service.findById(id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}