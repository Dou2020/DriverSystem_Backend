package com.DriverSystem_Back.modules.quotation;

import com.DriverSystem_Back.modules.quotation.dto.QuotationRequest;
import com.DriverSystem_Back.modules.quotation.dto.QuotationResponse;
import com.DriverSystem_Back.modules.quotation.dto.QuotationStatuRequest;

import java.util.List;

public interface IQuotationService {
  public QuotationResponse save(QuotationRequest request);
  public QuotationResponse update(QuotationRequest request);
  public void delete(Long id);
  public QuotationResponse get(Long id);
  public List<QuotationResponse> findAll();
  public Quotation updataStatus(QuotationStatuRequest request);
}
