package com.DriverSystem_Back.modules.Invoice;
import com.DriverSystem_Back.modules.Invoice.dto.InvoiceRequest;
import com.DriverSystem_Back.modules.Invoice.dto.InvoiceResponse;
import com.DriverSystem_Back.modules.Item.request.ItemRepository;
import com.DriverSystem_Back.modules.Item.response.ItemResponse;
import com.DriverSystem_Back.modules.Item.response.ItemResponseRepository;
import com.DriverSystem_Back.modules.quotation.Quotation;
import com.DriverSystem_Back.modules.quotation.QuotationRepository;
import com.DriverSystem_Back.modules.quotation.dto.QuotationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class InvoiceService implements IInvoiceService {
    @Autowired
    private InvoiceRepository invoiceRepository;
    @Autowired
    private ItemResponseRepository itemResponseRepository;
    @Autowired
    private QuotationRepository quotationRepository;

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
        invoice.setOutstandingBalance(BigDecimal.ZERO); // Initialize with 0, will be updated when total is calculated
        return  this.invoiceRepository.save(invoice).getId();
    }

    @Override
    public List<InvoiceResponse> findById(Long id) {
        List<Invoice>  invoiceList = this.invoiceRepository.findByUserId(id);
        
        System.out.println("=== INVOICES FOUND FOR USER " + id + " ===");
        for (Invoice inv : invoiceList) {
            System.out.println("Invoice ID: " + inv.getId() + ", Status: " + inv.getStatus() + 
                             ", Quotation ID: " + inv.getQuotation_id() + ", Total: " + inv.getTotal() + 
                             ", Outstanding: " + inv.getOutstandingBalance());
        }
        System.out.println("=====================================");
        
        // Group invoices by workOrderId
        Map<Long, List<Invoice>> groupedByWorkOrder = new HashMap<>();
        List<Invoice> invoicesWithoutWorkOrder = new ArrayList<>();
        
        for (Invoice invoice : invoiceList) {
            if (invoice.getQuotation_id() != null) {
                Optional<Quotation> quotationOpt = this.quotationRepository.findById(invoice.getQuotation_id());
                if (quotationOpt.isPresent()) {
                    Quotation quotation = quotationOpt.get();
                    Long workOrderId = quotation.getWorkOrderId();
                    if (workOrderId != null) {
                        groupedByWorkOrder.computeIfAbsent(workOrderId, k -> new ArrayList<>()).add(invoice);
                    } else {
                        // Quotation exists but has no workOrderId
                        invoicesWithoutWorkOrder.add(invoice);
                    }
                } else {
                    // Quotation doesn't exist
                    invoicesWithoutWorkOrder.add(invoice);
                }
            } else {
                // Invoice has no quotation_id
                invoicesWithoutWorkOrder.add(invoice);
            }
        }
        
        List<InvoiceResponse> invoiceResponseList = new ArrayList<>();
        
        // Process grouped invoices (with workOrderId)
        for (Map.Entry<Long, List<Invoice>> entry : groupedByWorkOrder.entrySet()) {
            List<Invoice> invoices = entry.getValue();
            
            if (invoices.size() == 1) {
                // Single invoice, process as before
                Invoice invoice = invoices.get(0);
                BigDecimal total = new BigDecimal(0);
                List<ItemResponse> itemResponse = this.itemResponseRepository.findByIdQuotationId(invoice.getQuotation_id());
                for (ItemResponse item : itemResponse) {
                    total = total.add(item.getSubtotal());
                }
                
                // Always update total in DB if different
                if (invoice.getTotal() == null || invoice.getTotal().compareTo(total) != 0) {
                    invoice.setTotal(total);
                    this.invoiceRepository.save(invoice);
                }
                
                // Initialize outstandingBalance if null
                if (invoice.getOutstandingBalance() == null) {
                    if ("ISSUED".equals(invoice.getStatus())) {
                        invoice.setOutstandingBalance(total);
                        this.invoiceRepository.save(invoice);
                    } else if ("PAID".equals(invoice.getStatus())) {
                        invoice.setOutstandingBalance(BigDecimal.ZERO);
                        this.invoiceRepository.save(invoice);
                    }
                }
                
                invoiceResponseList.add(new InvoiceResponse(invoice, itemResponse));
            } else {
                // Multiple invoices for same workOrderId
                // Check if any invoice has different status (like PARTIALLY_PAID)
                boolean hasDifferentStatuses = invoices.stream()
                    .map(Invoice::getStatus)
                    .distinct()
                    .count() > 1;
                
                if (hasDifferentStatuses) {
                    // If invoices have different statuses, show them individually
                    for (Invoice invoice : invoices) {
                        BigDecimal total = new BigDecimal(0);
                        List<ItemResponse> itemResponse = this.itemResponseRepository.findByIdQuotationId(invoice.getQuotation_id());
                        for (ItemResponse item : itemResponse) {
                            total = total.add(item.getSubtotal());
                        }
                        
                        // Always update total in DB if different
                        if (invoice.getTotal() == null || invoice.getTotal().compareTo(total) != 0) {
                            invoice.setTotal(total);
                            this.invoiceRepository.save(invoice);
                        }
                        
                        // Initialize outstandingBalance if null
                        if (invoice.getOutstandingBalance() == null) {
                            if ("ISSUED".equals(invoice.getStatus())) {
                                invoice.setOutstandingBalance(total);
                                this.invoiceRepository.save(invoice);
                            } else if ("PAID".equals(invoice.getStatus())) {
                                invoice.setOutstandingBalance(BigDecimal.ZERO);
                                this.invoiceRepository.save(invoice);
                            }
                        }
                        
                        invoiceResponseList.add(new InvoiceResponse(invoice, itemResponse));
                    }
                } else {
                    // All invoices have same status, combine them
                    Invoice combinedInvoice = invoices.get(0); // Use first invoice as base
                    List<ItemResponse> combinedItems = new ArrayList<>();
                    BigDecimal total = new BigDecimal(0);
                    
                    for (Invoice invoice : invoices) {
                        List<ItemResponse> itemResponse = this.itemResponseRepository.findByIdQuotationId(invoice.getQuotation_id());
                        combinedItems.addAll(itemResponse);
                        for (ItemResponse item : itemResponse) {
                            total = total.add(item.getSubtotal());
                        }
                    }
                    
                    // Always update total in DB if different
                    if (combinedInvoice.getTotal() == null || combinedInvoice.getTotal().compareTo(total) != 0) {
                        combinedInvoice.setTotal(total);
                        this.invoiceRepository.save(combinedInvoice);
                    }
                    
                    // Initialize outstandingBalance if null
                    if (combinedInvoice.getOutstandingBalance() == null) {
                        if ("ISSUED".equals(combinedInvoice.getStatus())) {
                            combinedInvoice.setOutstandingBalance(total);
                            this.invoiceRepository.save(combinedInvoice);
                        } else if ("PAID".equals(combinedInvoice.getStatus())) {
                            combinedInvoice.setOutstandingBalance(BigDecimal.ZERO);
                            this.invoiceRepository.save(combinedInvoice);
                        }
                    }
                    
                    invoiceResponseList.add(new InvoiceResponse(combinedInvoice, combinedItems));
                }
            }
        }
        
        // Process invoices without workOrderId (individual invoices)
        for (Invoice invoice : invoicesWithoutWorkOrder) {
            System.out.println("Processing invoice without workOrder: " + invoice.getId() + ", status: " + invoice.getStatus());
            BigDecimal total = new BigDecimal(0);
            List<ItemResponse> itemResponse = new ArrayList<>();
            
            if (invoice.getQuotation_id() != null) {
                itemResponse = this.itemResponseRepository.findByIdQuotationId(invoice.getQuotation_id());
                for (ItemResponse item : itemResponse) {
                    total = total.add(item.getSubtotal());
                }
            }
            
            // Always update total in DB if different
            if (invoice.getTotal() == null || invoice.getTotal().compareTo(total) != 0) {
                invoice.setTotal(total);
                this.invoiceRepository.save(invoice);
            }
            
            // Initialize outstandingBalance if null
            if (invoice.getOutstandingBalance() == null) {
                if ("ISSUED".equals(invoice.getStatus())) {
                    invoice.setOutstandingBalance(total);
                    this.invoiceRepository.save(invoice);
                } else if ("PAID".equals(invoice.getStatus())) {
                    invoice.setOutstandingBalance(BigDecimal.ZERO);
                    this.invoiceRepository.save(invoice);
                }
            }
            
            invoiceResponseList.add(new InvoiceResponse(invoice, itemResponse));
        }
        
        System.out.println("=== FINAL RESPONSE FOR USER " + id + " ===");
        for (InvoiceResponse resp : invoiceResponseList) {
            System.out.println("Response Invoice ID: " + resp.invoice().getId() + ", Status: " + resp.invoice().getStatus() + 
                             ", Total: " + resp.invoice().getTotal() + ", Outstanding: " + resp.invoice().getOutstandingBalance());
        }
        System.out.println("=====================================");
        
        return invoiceResponseList;
    }

    public InvoiceResponse getInvoiser(Long id) {
       Invoice invoice = this.invoiceRepository.findById(id).get();
        System.out.println("factura "+invoice.toString());
        List<ItemResponse> itemResponse = this.itemResponseRepository.findByIdQuotationId(invoice.getQuotation_id());
        
        // Calculate total for response only
        BigDecimal total = new BigDecimal(0);
        for (ItemResponse item : itemResponse) {
            total = total.add(item.getSubtotal());
        }
        
        // Always update total in DB if different
        if (invoice.getTotal() == null || invoice.getTotal().compareTo(total) != 0) {
            invoice.setTotal(total);
            this.invoiceRepository.save(invoice);
        }
        
        // Create response invoice without modifying DB
        Invoice responseInvoice = new Invoice();
        responseInvoice.setId(invoice.getId());
        responseInvoice.setCode(invoice.getCode());
        responseInvoice.setType(invoice.getType());
        responseInvoice.setQuotation_id(invoice.getQuotation_id());
        responseInvoice.setGoodsReceiptId(invoice.getGoodsReceiptId());
        responseInvoice.setUserId(invoice.getUserId());
        responseInvoice.setIssueDate(invoice.getIssueDate());
        responseInvoice.setDueDate(invoice.getDueDate());
        responseInvoice.setStatus(invoice.getStatus());
        responseInvoice.setCurrency(invoice.getCurrency());
        responseInvoice.setNotes(invoice.getNotes());
        responseInvoice.setTotal(total); // Use calculated total
        responseInvoice.setOutstandingBalance(invoice.getOutstandingBalance());

        return  new InvoiceResponse(responseInvoice,itemResponse);
    }


}

