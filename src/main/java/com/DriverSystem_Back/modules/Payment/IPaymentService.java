package com.DriverSystem_Back.modules.Payment;

import com.DriverSystem_Back.modules.Payment.dto.PaymentRequest;
import com.DriverSystem_Back.modules.Payment.dto.PaymentResponse;

import java.util.List;

public interface IPaymentService {
  public PaymentResponse save(PaymentRequest request);
  public void delete(Long id);
  public List<PaymentResponse> getPaymentUser(Long UserId);
  public List<PaymentResponse> findAll();
}
