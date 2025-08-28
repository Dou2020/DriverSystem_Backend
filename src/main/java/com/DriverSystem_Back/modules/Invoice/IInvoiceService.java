package com.DriverSystem_Back.modules.Invoice;

import com.DriverSystem_Back.modules.Invoice.dto.InvoiceRequest;
import com.DriverSystem_Back.modules.Invoice.dto.InvoiceResponse;

import java.util.List;

public interface IInvoiceService {
  public Long save(InvoiceRequest request);
  public List<InvoiceResponse> findById(Long id);
}
