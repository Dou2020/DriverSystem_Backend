package com.DriverSystem_Back.modules.Payment;
import com.DriverSystem_Back.modules.Invoice.Invoice;
import com.DriverSystem_Back.modules.Invoice.InvoiceService;
import com.DriverSystem_Back.modules.Payment.dto.PaymentRequest;
import com.DriverSystem_Back.modules.Payment.dto.PaymentResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentService  implements IPaymentService{
  @Autowired
    private PaymentRepository paymentRepository;
  @Autowired
    private InvoiceService invoiceService;

    @Override
    public PaymentResponse save(PaymentRequest request) {
       Payment payment = new Payment();
       payment.setAmount(request.amount());
       payment.setMethod(request.methodId());
        var invoiceId=  this.invoiceService.save(request.invoice());
        payment.setInvoice(invoiceId);
        this.paymentRepository.save(payment);
        return null;
    }

    @Override
    public void  delete(Long id) {
        this.paymentRepository.deleteById(id);
    }

    @Override
    public PaymentResponse get(PaymentRequest request) {
        return null;
    }
    @Override
    public List<PaymentResponse> findAll() {
        return List.of();
    }

}
