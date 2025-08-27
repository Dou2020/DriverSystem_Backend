package com.DriverSystem_Back.modules.paymentmethod;

import java.util.List;

public interface IPaymentMethodService {
//  public PaymentMethodResponse save(PaymentMethodRequest request);
//  public PaymentMethodResponse update(PaymentMethodRequest request);
//  public PaymentMethodResponse delete(Long id);
//  public PaymentMethodResponse get(PaymentMethodRequest request);
  public List<PaymentMethod> findAll();
}
