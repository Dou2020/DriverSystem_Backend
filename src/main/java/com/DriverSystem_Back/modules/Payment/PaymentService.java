package com.DriverSystem_Back.modules.Payment;
import com.DriverSystem_Back.modules.Invoice.Invoice;
import com.DriverSystem_Back.modules.Invoice.InvoiceRepository;
import com.DriverSystem_Back.modules.Invoice.InvoiceService;
import com.DriverSystem_Back.modules.Invoice.dto.InvoiceResponse;
import com.DriverSystem_Back.modules.Payment.dto.PaymentRequest;
import com.DriverSystem_Back.modules.Payment.dto.PaymentResponse;
import com.DriverSystem_Back.modules.Payment.view.PaymentView;
import com.DriverSystem_Back.modules.Payment.view.PaymentViewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class PaymentService  implements IPaymentService{
  @Autowired
    private PaymentRepository paymentRepository;
  @Autowired
    private InvoiceRepository invoiceRepository;
  @Autowired
    private PaymentViewRepository paymentResponseViewRepository;
  @Autowired
    private InvoiceService  invoiceService;

    @Override
    public PaymentResponse save(PaymentRequest request) {
       Payment payment = new Payment();
       payment.setAmount(request.amount());
       payment.setMethod(request.methodId());
       payment.setInvoice(request.invoice());
       payment.setReference(request.reference());
       Invoice invoice = this.invoiceRepository.findById(request.invoice()).get();
      if(invoice.getStatus().equals("ISSUED")){
          if(request.amount().compareTo(invoice.getTotal()) ==0){
              invoice.setStatus("PAID");
              invoice.setOutstandingBalance(BigDecimal.ZERO);
          } else if (request.amount().compareTo(invoice.getTotal()) < 0) {
              invoice.setStatus("PARTIALLY_PAID");
              invoice.setOutstandingBalance(invoice.getTotal().subtract(request.amount()));
          }
          else{
              return null;
          }
      } else if (invoice.getStatus().equals("PARTIALLY_PAID")) {
          if(request.amount().compareTo(invoice.getOutstandingBalance()) ==0){
              invoice.setStatus("PAID");
              invoice.setOutstandingBalance(BigDecimal.ZERO);
          } else if (request.amount().compareTo(invoice.getTotal()) < 0) {
              invoice.setOutstandingBalance(invoice.getOutstandingBalance().subtract(request.amount()));
          }
          else{
              return null;
          }
      }

        this.invoiceRepository.save(invoice);
       this.paymentRepository.save(payment);
       return null;
    }

    @Override
    public void  delete(Long id) {
        this.paymentRepository.deleteById(id);
    }

    @Override
    public List<PaymentResponse> getPaymentUser(Long userId) {
        List<PaymentView>  pr= this.paymentResponseViewRepository.findByUserId(userId);
        List<PaymentResponse>  response = new ArrayList<>();
        for(PaymentView p:pr){
            InvoiceResponse invoice = this.invoiceService.getInvoiser(p.getInvoiceId());
            response.add(new PaymentResponse(p, invoice));
        }
        return response;
    }
    @Override
    public List<PaymentResponse> findAll() {
        return List.of();
    }

}
