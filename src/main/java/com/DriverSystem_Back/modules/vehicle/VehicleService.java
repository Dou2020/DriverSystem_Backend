package com.DriverSystem_Back.modules.vehicle;

import com.DriverSystem_Back.exception.HttpException;
import com.DriverSystem_Back.modules.vehiclevisit.VehicleVisit;
import com.DriverSystem_Back.modules.vehiclevisit.VehicleVisitRepository;
import com.DriverSystem_Back.modules.vehicle.dto.VehicleCreateRequest;
import com.DriverSystem_Back.modules.vehicle.dto.VehicleRequest;
import com.DriverSystem_Back.modules.vehicle.view.VehicleResponse;
import com.DriverSystem_Back.modules.vehicle.view.VehicleResponseRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Optional;

@Service
public class VehicleService implements IVehicleService {

     @Autowired
     private VehicleRepository vehicleRepository;
     @Autowired
     private ModelMapper modelMapper;
     @Autowired
     private VehicleResponseRepository vehicleResponseRepository;
     @Autowired
     private VehicleVisitRepository vehicleVisitRepository;

    @Override
    public VehicleResponse getVehicle(String plate) {
        return this.vehicleResponseRepository.findByPlate(plate)
                .orElseThrow(() -> new HttpException("Vehículo no encontrado", HttpStatus.NOT_FOUND));
    }

    @Override
    public VehicleResponse saveVehicle(VehicleRequest request) {
        Optional<Vehicle> vehicle1 = this.vehicleRepository.findByPlate(request.plate());
        if(vehicle1.isPresent())
            throw  new HttpException("El vehicula ya esta registrado",HttpStatus.UNPROCESSABLE_ENTITY);
        Vehicle vehicle2= this.modelMapper.map(request, Vehicle.class);
        Vehicle  vehicle =this.vehicleRepository.save(vehicle2);
        return this.vehicleResponseRepository.findById(vehicle.getId())
                .orElseThrow(() -> new HttpException("Error al recuperar el vehículo creado", HttpStatus.INTERNAL_SERVER_ERROR));
    }

    public VehicleResponse createVehicle(VehicleCreateRequest request) {
        Optional<Vehicle> existingVehicle = this.vehicleRepository.findByPlate(request.plate());
        if(existingVehicle.isPresent())
            throw  new HttpException("El vehículo ya está registrado", HttpStatus.UNPROCESSABLE_ENTITY);
        
        // Crear nueva instancia de Vehicle sin ID
        Vehicle vehicle = new Vehicle();
        vehicle.setVin(request.vin());
        vehicle.setPlate(request.plate());
        vehicle.setMakeId(request.makeId());
        vehicle.setModelId(request.modelId());
        vehicle.setColor(request.color());
        vehicle.setModelYear(request.modelYear());
        
        Vehicle savedVehicle = this.vehicleRepository.save(vehicle);
        
        // Crear automáticamente una VehicleVisit
        VehicleVisit vehicleVisit = new VehicleVisit();
        vehicleVisit.setVehicleId(savedVehicle.getId());
        vehicleVisit.setCustomerId(request.customerId());
        vehicleVisit.setArrivedAt(OffsetDateTime.now(ZoneOffset.of("-06:00")));
        vehicleVisit.setStatus("NUEVA");
        vehicleVisit.setNotes("Visita creada automáticamente al registrar el vehículo");
        
        this.vehicleVisitRepository.save(vehicleVisit);
        
        return this.vehicleResponseRepository.findById(savedVehicle.getId())
                .orElseThrow(() -> new HttpException("Error al recuperar el vehículo creado", HttpStatus.INTERNAL_SERVER_ERROR));
    }

    @Override
    public void deleteVehicle(Long idVehicle) {
        this.validationVehicle(idVehicle);
        this.vehicleRepository.deleteById(idVehicle);
    }

    @Override
    public VehicleResponse updateVehicle(VehicleRequest request) {
       Vehicle  vehicle1=  this.validationVehicle(request.id()).get();
        vehicle1.setVin(request.vin());
        vehicle1.setPlate(request.plate());
        vehicle1.setMakeId(request.makeId());
        vehicle1.setModelId(request.modelId());
        vehicle1.setModelYear(request.modelYear());
        vehicle1.setColor(request.color());
        try {
            vehicleRepository.save(vehicle1);
        } catch (DataIntegrityViolationException e) {
            throw new HttpException(e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
        }
        VehicleResponse vehicle2 = this.vehicleResponseRepository.findById(request.id()).get();
        return  vehicle2;
    }

    @Override
    public List<VehicleResponse> findAllVehicle() {
        return this.vehicleResponseRepository.findAll();
    }

    public List<VehicleResponse> findUnassignedVehicles() {
        return this.vehicleResponseRepository.findUnassignedVehicles();
    }

    public  Optional<Vehicle> validationVehicle(Long idVehicle){
        Optional<Vehicle> vehicle = vehicleRepository.findById(idVehicle);
        if(vehicle.isEmpty())
            throw  new HttpException("Vehiculo no encontrado!", HttpStatus.NOT_FOUND);
        return vehicle;
    }

}
