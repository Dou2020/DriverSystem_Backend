package com.DriverSystem_Back.modules.Invoice;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/invoice")
@AllArgsConstructor
public class InvoiceController {
    private InvoiceService invoiceService;

    @GetMapping("/user/{id}")
    public ResponseEntity<?>  getUser(@PathVariable Long id){
        return ResponseEntity.ok(invoiceService.findById(id));
    }
}
