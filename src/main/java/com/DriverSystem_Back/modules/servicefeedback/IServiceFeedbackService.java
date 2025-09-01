package com.DriverSystem_Back.modules.servicefeedback;

import com.DriverSystem_Back.modules.servicefeedback.dto.ServiceFeedbackRequest;

import java.util.List;
import java.util.Map;

public interface IServiceFeedbackService {
  public Object save(ServiceFeedbackRequest request);
  public Object  update(ServiceFeedbackRequest request);
  public void delete(Long id);
  public  List<Map<String, Object>>findAll(Long customerId);
}
