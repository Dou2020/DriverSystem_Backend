package com.DriverSystem_Back.modules.quotation;
import com.DriverSystem_Back.modules.quotation.dto.QuotationRequest;
import com.DriverSystem_Back.modules.quotation.dto.QuotationStatuRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/quotation")
@AllArgsConstructor
public class QuotationController {
    private QuotationService quotationService;

    @PostMapping("/")
    public ResponseEntity<?> QuotationSave(@RequestBody @Valid QuotationRequest request){
        return ResponseEntity.ok(this.quotationService.save(request));
     }
    @PostMapping("/status")
    public ResponseEntity<?> UpdateQuotationStatus(@RequestBody @Valid QuotationStatuRequest request){
        return ResponseEntity.ok( this.quotationService.updataStatus(request));
    }

    @DeleteMapping("/{id}")
    public  ResponseEntity<?> DeleteQuotation(@PathVariable Long id){
        this.quotationService.delete(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> Quotation(@PathVariable Long id){
        return ResponseEntity.ok(this.quotationService.get(id));
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<?> QuotationUser(@PathVariable Long id){
        return ResponseEntity.ok(this.quotationService.findAllByUserId(id));
    }


}
