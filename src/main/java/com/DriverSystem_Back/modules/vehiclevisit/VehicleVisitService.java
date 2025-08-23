package com.DriverSystem_Back.modules.vehiclevisit;
import com.DriverSystem_Back.exception.HttpException;
import com.DriverSystem_Back.modules.user.User;
import com.DriverSystem_Back.modules.user.UserRepository;
import com.DriverSystem_Back.modules.user.UserService;
import com.DriverSystem_Back.modules.vehicle.Vehicle;
import com.DriverSystem_Back.modules.vehicle.VehicleRepository;
import com.DriverSystem_Back.modules.vehicle.VehicleService;
import com.DriverSystem_Back.modules.vehicle.dto.VehicleVisitResponse;
import com.DriverSystem_Back.modules.vehiclemake.VehicleMake;
import com.DriverSystem_Back.modules.vehiclemake.VehicleMakeRepository;
import com.DriverSystem_Back.modules.vehiclemodel.VehicleModel;
import com.DriverSystem_Back.modules.vehiclemodel.VehicleModelRepository;
import com.DriverSystem_Back.modules.workorder.WorkOrder;
import com.DriverSystem_Back.modules.workorder.WorkOrderRepository;
import com.DriverSystem_Back.modules.vehiclevisit.dto.VehicleVisitRequest;
import com.DriverSystem_Back.modules.vehiclevisit.dto.VehicleVisitCreateRequest;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class VehicleVisitService implements IVehicleVisitService {
    @Autowired
    private VehicleVisitRepository vehicleVisitRepository;

    @Autowired
     private ModelMapper modelMapper;

    @Autowired
    private UserService userService;
    @Autowired
    private VehicleService vehicleService;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private VehicleRepository vehicleRepository;
    
    @Autowired
    private VehicleMakeRepository vehicleMakeRepository;
    
    @Autowired
    private VehicleModelRepository vehicleModelRepository;
    
    @Autowired
    private WorkOrderRepository workOrderRepository;

    @Override
    public VehicleVisit saveVehicleVisit(VehicleVisitRequest vehicleVisit) {
        this.userService.validateUser(vehicleVisit.customerId());
        this.vehicleService.validationVehicle(vehicleVisit.vehicleId());
        VehicleVisit vehicleVisit1=  modelMapper.map(vehicleVisit, VehicleVisit.class);
        vehicleVisit1.setArrivedAt(OffsetDateTime.now(ZoneOffset.of("-06:00")));
        vehicleVisit1.setStatus("NUEVA"); // Set default status
        return this.vehicleVisitRepository.save(vehicleVisit1);
    }

    /**
     * Crear una nueva visita de vehículo (sin ID)
     */
    public VehicleVisit saveVehicleVisit(VehicleVisitCreateRequest vehicleVisitRequest) {
        this.userService.validateUser(vehicleVisitRequest.customerId());
        this.vehicleService.validationVehicle(vehicleVisitRequest.vehicleId());
        
        // Crear nueva entidad sin ID (será autogenerado)
        VehicleVisit vehicleVisit = new VehicleVisit();
        vehicleVisit.setVehicleId(vehicleVisitRequest.vehicleId());
        vehicleVisit.setCustomerId(vehicleVisitRequest.customerId());
        vehicleVisit.setArrivedAt(OffsetDateTime.now(ZoneOffset.of("-06:00")));
        
        // Convertir LocalDateTime a OffsetDateTime si está presente
        if (vehicleVisitRequest.departedAt() != null) {
            vehicleVisit.setDepartureAt(vehicleVisitRequest.departedAt().atOffset(ZoneOffset.of("-06:00")));
        }
        
        vehicleVisit.setNotes(vehicleVisitRequest.notes());
        vehicleVisit.setStatus("NUEVA"); // Set default status
        
        return this.vehicleVisitRepository.save(vehicleVisit);
    }

    public List<VehicleVisitResponse> getAllVehicleVisits() {
        return vehicleVisitRepository.findAll().stream()
                .map(this::mapToVehicleVisitResponse)
                .collect(Collectors.toList());
    }

    public VehicleVisit updateVisitStatus(Long id, String newStatus) {
        VehicleVisit vehicleVisit = validation(id).get();
        
        // Si el estado cambia a "EN_PROCESO", crear automáticamente una work_order
        if ("EN_PROCESO".equals(newStatus.toUpperCase()) && !"EN_PROCESO".equals(vehicleVisit.getStatus())) {
            createWorkOrderFromVisit(vehicleVisit);
        }
        
        // Actualizar el estado (incluyendo CANCELADA sin eliminar)
        vehicleVisit.setStatus(newStatus);
        return this.vehicleVisitRepository.save(vehicleVisit);
    }
    
    /**
     * Crea una WorkOrder automáticamente basada en una VehicleVisit
     */
    private void createWorkOrderFromVisit(VehicleVisit vehicleVisit) {
        WorkOrder workOrder = new WorkOrder();
        
        // Asignar información básica
        workOrder.setVehicleId(vehicleVisit.getVehicleId());
        workOrder.setCustomerId(vehicleVisit.getCustomerId());
        workOrder.setVisitId(vehicleVisit.getId()); // Asignar el visit_id
        
        // Valores por defecto (puedes ajustar según tus necesidades)
        workOrder.setTypeId(1L); // ID por defecto de maintenance_type, ajustar según tu BD
        workOrder.setStatusType(1L); // ID por defecto de work_status, ajustar según tu BD
        workOrder.setDescription("Orden de trabajo creada automáticamente desde visita #" + vehicleVisit.getId());
        workOrder.setEstimatedHours(new BigDecimal("2.00")); // 2 horas por defecto
        workOrder.setCreatedBy(vehicleVisit.getCustomerId()); // Usar el customer como creator por defecto
        
        // El código se genera automáticamente por el trigger en la BD
        // opened_at se establece automáticamente por defecto en la BD
        
        this.workOrderRepository.save(workOrder);
    }

    private VehicleVisitResponse mapToVehicleVisitResponse(VehicleVisit visit) {
        // Obtener datos del usuario/cliente
        String customerName = "N/A";
        Optional<User> userOpt = userRepository.findById(visit.getCustomerId());
        if (userOpt.isPresent()) {
            customerName = userOpt.get().getName();
        }
        
        // Obtener datos del vehículo
        String vehiclePlate = "N/A";
        String vehicleMake = "N/A";
        String vehicleModel = "N/A";
        
        Optional<Vehicle> vehicleOpt = vehicleRepository.findById(visit.getVehicleId());
        if (vehicleOpt.isPresent()) {
            Vehicle vehicle = vehicleOpt.get();
            vehiclePlate = vehicle.getPlate() != null ? vehicle.getPlate() : "N/A";
            
            // Obtener el make del vehículo
            if (vehicle.getMakeId() != null) {
                Optional<VehicleMake> makeOpt = vehicleMakeRepository.findById(vehicle.getMakeId());
                if (makeOpt.isPresent()) {
                    vehicleMake = makeOpt.get().getName();
                }
            }
            
            // Obtener el model del vehículo
            if (vehicle.getModelId() != null) {
                Optional<VehicleModel> modelOpt = vehicleModelRepository.findById(vehicle.getModelId());
                if (modelOpt.isPresent()) {
                    vehicleModel = modelOpt.get().getName();
                }
            }
        }
        
        return new VehicleVisitResponse(
            visit.getId(),
            visit.getVehicleId(),
            vehiclePlate,
            vehicleMake,
            vehicleModel,
            visit.getCustomerId(),
            customerName,
            visit.getArrivedAt().toLocalDateTime(),
            visit.getDepartureAt() != null ? visit.getDepartureAt().toLocalDateTime() : null,
            visit.getNotes(),
            visit.getStatus() != null ? visit.getStatus() : "NUEVA"
        );
    }

    @Override
    public VehicleVisit updateVehicleVisit(VehicleVisitRequest request) {
        VehicleVisit vehicleVisit = validation(request.id()).get();
        vehicleVisit.setVehicleId(request.vehicleId());
        vehicleVisit.setCustomerId(request.customerId());
        vehicleVisit.setDepartureAt(request.departedAt());
        vehicleVisit.setNotes(request.notes());
        return this.vehicleVisitRepository.save(vehicleVisit);
    }

    @Override
    public  void deleteVehicleVisit(Long id) {
       this.validation(id);
       this.vehicleVisitRepository.deleteById(id);
    }

    @Override
    public VehicleVisit getVehicleVisitById(Long id) {
        this.validation(id);
        return this.vehicleVisitRepository.getReferenceById(id);
    }

    @Override
    public void updataPartedAt(Long id) {
        VehicleVisit vehicleVisit= this.validation(id).get();
        vehicleVisit.setDepartureAt(OffsetDateTime.now(ZoneOffset.of("-06:00")));
        this.vehicleVisitRepository.save(vehicleVisit);
    }

    public Optional<VehicleVisit> validation(Long id) {
         Optional<VehicleVisit> optional = this.vehicleVisitRepository.findById(id);
         if(optional.isEmpty())
             throw  new HttpException("Este registro no existe!", HttpStatus.NOT_FOUND);
        return optional;
    }

}
