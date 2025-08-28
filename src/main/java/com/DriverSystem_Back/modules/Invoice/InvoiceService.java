package com.DriverSystem_Back.modules.Invoice;
import com.DriverSystem_Back.modules.Invoice.dto.InvoiceRequest;
import com.DriverSystem_Back.modules.Invoice.dto.InvoiceResponse;
import com.DriverSystem_Back.modules.Item.request.ItemRepository;
import com.DriverSystem_Back.modules.Item.response.ItemResponse;
import com.DriverSystem_Back.modules.Item.response.ItemResponseRepository;
import com.DriverSystem_Back.modules.quotation.dto.QuotationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class InvoiceService implements IInvoiceService {
    @Autowired
    private InvoiceRepository invoiceRepository;
    @Autowired
    private ItemResponseRepository itemResponseRepository;

    @Override
    public Long save(InvoiceRequest request) {
        Invoice invoice = new Invoice();
        invoice.setType(request.type());
      //  invoice.setWorkOrderId(request.workOrderId());
        invoice.setGoodsReceiptId(request.goodsReceiptId());
        invoice.setUserId(request.userId());
        invoice.setNotes(request.notes());
        invoice.setDueDate(request.dueDate());
        invoice.setIssueDate(LocalDate.now());
        invoice.setStatus("ISSUED");
        invoice.setCurrency("GTQ");
        return  this.invoiceRepository.save(invoice).getId();
    }

    @Override
    public List<InvoiceResponse> findById(Long id) {
        List<Invoice>  invoiceList = this.invoiceRepository.findByUserId(id);
        List<InvoiceResponse> invoiceResponseList = new ArrayList<>();
        for (Invoice invoice : invoiceList) {
            BigDecimal total = new BigDecimal(0);
            List<ItemResponse> itemResponse = this.itemResponseRepository.findByIdQuotationId(invoice.getQuotation_id());
             for (ItemResponse item : itemResponse) {
                 total = total.add(item.getSubtotal());
             }
             invoice.setTotal(total);
             this.invoiceRepository.save(invoice);
            invoiceResponseList.add(new InvoiceResponse(invoice,itemResponse));
        }
        return invoiceResponseList;
    }

    public InvoiceResponse getInvoiser(Long id) {
       Invoice invoice = this.invoiceRepository.findById(id).get();
        System.out.println("factura "+invoice.toString());
        List<InvoiceResponse> invoiceResponseList = new ArrayList<>();
            List<ItemResponse> itemResponse = this.itemResponseRepository.findByIdQuotationId(invoice.getQuotation_id());

        return  new InvoiceResponse(invoice,itemResponse);
    }


}

