package com.DriverSystem_Back.modules.Payment;
import com.DriverSystem_Back.modules.Payment.dto.PaymentRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/payment")
@AllArgsConstructor
public class PaymentController {
    private PaymentService paymentService;

    @PostMapping("/")
    public ResponseEntity<?> save (@RequestBody @Valid PaymentRequest request){
         this.paymentService.save(request);
        return ResponseEntity.ok().build();
    }
    @GetMapping("/{userId}")
    public  ResponseEntity<?> get(@PathVariable Long userId){
        return  ResponseEntity.ok(this.paymentService.getPaymentUser(userId));
    }

}
