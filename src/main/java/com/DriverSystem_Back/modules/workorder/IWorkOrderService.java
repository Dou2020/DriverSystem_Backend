package com.DriverSystem_Back.modules.workorder;

import com.DriverSystem_Back.modules.workorder.dto.WorkOrderRequest;
import com.DriverSystem_Back.modules.workorder.view.WorkOrderResponde;

import java.util.List;

public interface IWorkOrderService{
    public WorkOrder save(WorkOrderRequest workOrder);
    public WorkOrder update(WorkOrderRequest workOrder);
    public void delete(Long id);
    public WorkOrder findById(Long id);
    public List<WorkOrderResponde> getWorkOrder();
    public List<WorkOrderResponde> findBystatus(Long id);
}
