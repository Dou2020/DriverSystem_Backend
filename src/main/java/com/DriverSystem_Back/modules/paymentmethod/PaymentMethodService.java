package com.DriverSystem_Back.modules.paymentmethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentMethodService implements IPaymentMethodService {
    @Autowired
    private PaymentMethodRepository paymentMethodRepository;
    @Override
    public List<PaymentMethod> findAll() {
        return this.paymentMethodRepository.findAll();
    }
}
