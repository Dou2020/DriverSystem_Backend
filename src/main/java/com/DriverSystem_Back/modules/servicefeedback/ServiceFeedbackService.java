package com.DriverSystem_Back.modules.servicefeedback;
import com.DriverSystem_Back.exception.HttpException;
import com.DriverSystem_Back.modules.servicefeedback.dto.ServiceFeedbackRequest;
import com.DriverSystem_Back.modules.workorder.WorkOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ServiceFeedbackService implements IServiceFeedbackService {
    @Autowired
    private ServiceFeedbackRepository serviceFeedbackRepository;
    @Autowired
    private WorkOrderRepository workOrderRepository;
    @Override
    public Object save(ServiceFeedbackRequest request) {
        // 1. Validar que la orden de trabajo exista y esté relacionada con el cliente
        boolean workOrderExistsForCustomer = workOrderRepository.existsByIdAndCustomerId(
                request.work_order_id(),
                request.customer_id()
        );

        if (!workOrderExistsForCustomer) {
            throw new HttpException("La orden de trabajo no pertenece al cliente", HttpStatus.BAD_REQUEST);
        }

        // 2. Validar que el cliente no haya dejado feedback previamente
        boolean feedbackExists = serviceFeedbackRepository.existsByWorkOrderIdAndCustomerId(
                request.work_order_id(),
                request.customer_id()
        );

        if (feedbackExists) {
            throw new HttpException("Ya se ha registrado feedback para esta orden de trabajo", HttpStatus.BAD_REQUEST);
        }

        // 3. Crear y guardar el feedback
        ServiceFeedback serviceFeedback = new ServiceFeedback();
        serviceFeedback.setComment(request.comment());
        serviceFeedback.setCustomerId(request.customer_id());
        serviceFeedback.setWorkOrderId(request.work_order_id());
        serviceFeedback.setRating(request.rating());

        try {
            serviceFeedbackRepository.save(serviceFeedback);
        } catch(Exception e){
            throw new HttpException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return serviceFeedback; // opcional, devuelve el objeto guardado
    }

    @Override
    public Object update(ServiceFeedbackRequest request) {
        return null;
    }

    @Override
    public  void  delete(Long id) {
         this.serviceFeedbackRepository.deleteById(id);
    }

    @Override
    public List<Map<String, Object>> findAll(Long customerId) {
        return this.serviceFeedbackRepository.findCustomFeedbackByCustomerId(customerId);
    }


}
