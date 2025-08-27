package com.DriverSystem_Back.modules.workorder;

import com.DriverSystem_Back.exception.HttpException;
import com.DriverSystem_Back.modules.user.User;
import com.DriverSystem_Back.modules.uservehicle.dto.UserVehicleRequest;
import com.DriverSystem_Back.modules.vehiclevisit.VehicleVisit;
import com.DriverSystem_Back.modules.vehiclevisit.VehicleVisitRepository;
import com.DriverSystem_Back.modules.workorder.dto.WorkOrderRequest;
import com.DriverSystem_Back.modules.workorder.dto.WorkOrderUserStateRequest;
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
    @Autowired
    private VehicleVisitRepository vehicleVisitRepository;
    @Override
    public WorkOrderResponde save(WorkOrderRequest request) {
        WorkOrder workOrderEntity = modelMapper.map(request, WorkOrder.class);
        workOrderEntity.setClosedAt(null);
        
        // Asignar automáticamente un visitId basado en el vehículo y cliente
        Long visitId = getOrCreateActiveVisit(request.vehicleId(), request.customerId());
        workOrderEntity.setVisitId(visitId);
        
        var id = this.workOrderRepository.save(workOrderEntity).getId();
        return this.workOrderRespondeRepository.findById(id).get();
    }
    
    /**
     * Obtiene una visita activa para el vehículo y cliente, o devuelve null si no existe
     * En un escenario real, podrías crear automáticamente una nueva visita si no existe una activa
     */
    private Long getOrCreateActiveVisit(Long vehicleId, Long customerId) {
        // Buscar una visita activa (sin fecha de salida) para este vehículo y cliente
        Optional<VehicleVisit> activeVisit = vehicleVisitRepository
            .findByVehicleIdAndCustomerIdAndDepartureAtIsNull(vehicleId, customerId);
        
        if (activeVisit.isPresent()) {
            return activeVisit.get().getId();
        }
        
        // Si no hay visita activa, podríamos crear una nueva automáticamente
        // o devolver null y manejar esto según la lógica del negocio
        return null; // Por ahora devolvemos null si no hay visita activa
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
        
        // Actualizar visitId si viene en el request
        if (request.visitId() != null) {
            workOrder.setVisitId(request.visitId());
        }
        
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

    /* si se quisiera filtar por usuario y estado  las ordenes seria agregar aca el id del estado*/
    @Override
    public List<WorkOrderResponde> workOrderUserId(WorkOrderUserStateRequest request) {
        List<WorkOrder> list = workOrderRepository.findByCustomerIdAndStatusType((request.userId()), request.statusId());
        List<WorkOrderResponde> workOrderRespondes = new ArrayList<>();
        for(WorkOrder workOrder : list){
            workOrderRespondes.add(this.workOrderRespondeRepository.findById(workOrder.getId()).get());
        }
        return workOrderRespondes;
    }

}
