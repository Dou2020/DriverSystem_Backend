package com.DriverSystem_Back.modules.workorder;

import com.DriverSystem_Back.exception.HttpException;
import com.DriverSystem_Back.modules.user.User;
import com.DriverSystem_Back.modules.uservehicle.dto.UserVehicleRequest;
import com.DriverSystem_Back.modules.workorder.dto.WorkOrderRequest;
import com.DriverSystem_Back.modules.workorder.view.WorkOrderResponde;
import com.DriverSystem_Back.modules.workorder.view.WorkOrderRespondeRepository;
import com.DriverSystem_Back.modules.workstatus.WorkStatus;
import com.DriverSystem_Back.modules.workstatus.WorkStatusRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class WorkOrderServidor implements IWorkOrderService {
    @Autowired
    private WorkOrderRepository workOrderRepository;
    @Autowired
    private WorkOrderRespondeRepository workOrderRespondeRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private WorkStatusRepository  workStatusRepository;
    @Override
    public WorkOrderResponde save(WorkOrderRequest request) {
        WorkOrder workOrderEntity = modelMapper.map(request, WorkOrder.class);
        workOrderEntity.setClosedAt(null);
        var id=  this.workOrderRepository.save(workOrderEntity).getId();
       return this.workOrderRespondeRepository.findById(id).get();
    }

    @Override
    public WorkOrderResponde update(WorkOrderRequest request) {
        WorkOrder workOrder = this.workOrderRepository.findById(request.id())
                .orElseThrow(() -> new HttpException("Work ordeer no encontrado!", HttpStatus.NOT_FOUND));
      //  workOrder.setCode(request.code());
        workOrder.setVehicleId(request.vehicleId());
        workOrder.setCustomerId(request.customerId());
        workOrder.setStatusType(request.statusType());
        workOrder.setTypeId(request.typeId());
        workOrder.setDescription(request.description());
        workOrder.setEstimatedHours(request.estimatedHours());
        workOrder.setClosedAt(request.closedAt());
        workOrder.setCreatedBy(request.createdBy());
        var id = this.workOrderRepository.save(workOrder).getId();
        return this.workOrderRespondeRepository.findById(id).get();
    }

    @Override
    public void delete(Long id) {
        this.workOrderRepository.findById(id).orElseThrow(() ->  new HttpException("Este registro no existe!", HttpStatus.NOT_FOUND));
        this.workOrderRepository.deleteById(id);
    }

    @Override
    public WorkOrder findById(Long id) {
        return this.workOrderRepository.findById(id).orElseThrow(() ->  new HttpException("Este registro no existe!", HttpStatus.NOT_FOUND));
    }

    @Override
    public List<WorkOrderResponde> getWorkOrder() {
        return this.workOrderRespondeRepository.findAll();
    }

    @Override
    public List<WorkOrderResponde> findBystatus(Long id) {
        Optional<WorkStatus>  workStatus = this.workStatusRepository.findById(id);
         if (workStatus.isEmpty())
             throw  new HttpException("Este registro no existe!", HttpStatus.NOT_FOUND);
        return this.workOrderRespondeRepository.findByStatus(workStatus.get().getName());
    }

    @Override
    public List<WorkOrderResponde> workOrderVehicle(UserVehicleRequest request) {
        List<WorkOrder> list = workOrderRepository.findByVehicleIdAndCustomerId(request.vehicleId(), request.userId());
        List<WorkOrderResponde> workOrderRespondes = new ArrayList<>();
        for(WorkOrder workOrder : list){
            System.out.println(workOrder.getId());
            workOrderRespondes.add(this.workOrderRespondeRepository.findById(workOrder.getId()).get());
        }
        return workOrderRespondes;
    }


}
