package com.DriverSystem_Back.modules.servicefeedback;
import com.DriverSystem_Back.modules.servicefeedback.dto.ServiceFeedbackRequest;
import com.DriverSystem_Back.modules.user.User;
import com.DriverSystem_Back.modules.user.dto.UserRequest;
import com.DriverSystem_Back.modules.user.dto.UserResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/service/feedback")
@AllArgsConstructor
public class ServiceFeedbackController {
  private IServiceFeedbackService serviceFeedbackService;

  @PostMapping("/register")
  public ResponseEntity<?> RegisterFeedback(@RequestBody @Valid ServiceFeedbackRequest body) {
      return ResponseEntity.ok().body(this.serviceFeedbackService.save(body));

  }
    @GetMapping("/{userId}")
    public ResponseEntity<?> getAllFeedback(@PathVariable Long userId) {
        return ResponseEntity.ok(this.serviceFeedbackService.findAll(userId));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteFeedback(@PathVariable Long id) {
      this.serviceFeedbackService.delete(id);
      return ResponseEntity.ok().build();
    }
}
