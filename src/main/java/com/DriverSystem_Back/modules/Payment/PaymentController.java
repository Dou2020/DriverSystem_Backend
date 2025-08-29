package com.DriverSystem_Back.modules.Payment;
import com.DriverSystem_Back.modules.Payment.dto.PaymentRequest;
import com.DriverSystem_Back.modules.Payment.dto.PaymentResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/payment")
@AllArgsConstructor
public class PaymentController {
    private PaymentService paymentService;

    @PostMapping("/")
    public ResponseEntity<PaymentResponse> save (@RequestBody @Valid PaymentRequest request){
         PaymentResponse response = this.paymentService.save(request);
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/{userId}")
    public ResponseEntity<?> get(@PathVariable Long userId){
        return ResponseEntity.ok(this.paymentService.getPaymentUser(userId));
    }
}
