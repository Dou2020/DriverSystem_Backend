package com.DriverSystem_Back.modules.paymentmethod;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/payment/method")
@AllArgsConstructor
public class PaymentMethodController {
     private PaymentMethodService paymentMethodService;

    @GetMapping("/")
    public ResponseEntity<?> getPaymentMethod(){
        return ResponseEntity.ok(this.paymentMethodService.findAll());
    }

}
