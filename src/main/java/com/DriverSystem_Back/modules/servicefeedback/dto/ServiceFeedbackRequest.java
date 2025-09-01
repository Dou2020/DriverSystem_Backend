package com.DriverSystem_Back.modules.servicefeedback.dto;

public record ServiceFeedbackRequest (
         Long work_order_id,
         Long  customer_id,
         short  rating,
         String comment
) {
}
