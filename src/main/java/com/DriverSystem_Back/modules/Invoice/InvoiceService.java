package com.DriverSystem_Back.modules.Invoice;
import com.DriverSystem_Back.modules.Invoice.dto.InvoiceRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class InvoiceService implements IInvoiceService {
    @Autowired
    private InvoiceRepository invoiceRepository;

    @Override
    public Long save(InvoiceRequest request) {
        Invoice invoice = new Invoice();
        invoice.setType(request.type());
        invoice.setWorkOrderId(request.workOrderId());
        invoice.setGoodsReceiptId(request.goodsReceiptId());
        invoice.setUserId(request.userId());
        invoice.setNotes(request.notes());
        invoice.setDueDate(request.dueDate());
        invoice.setIssueDate(LocalDate.now());
        invoice.setStatus("ISSUED");
        invoice.setCurrency("GTQ");
        return  this.invoiceRepository.save(invoice).getId();
    }

}
