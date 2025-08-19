package com.DriverSystem_Back.modules.vehicle;

import com.DriverSystem_Back.exception.HttpException;
import com.DriverSystem_Back.modules.vehicle.dto.VehicleRequest;
import com.DriverSystem_Back.modules.vehicle.view.VehicleResponse;
import com.DriverSystem_Back.modules.vehicle.view.VehicleResponseRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class VehicleService implements IVehicleService {

     @Autowired
     private VehicleRepository vehicleRepository;
     @Autowired
     private ModelMapper modelMapper;
     @Autowired
     private VehicleResponseRepository vehicleResponseRepository;

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
        return this.vehicleResponseRepository.getReferenceById(vehicle.getId());
    }

    @Override
    public void deleteVehicle(Long idVehicle) {
        this.validationVehicle(idVehicle);
        this.vehicleRepository.deleteById(idVehicle);
    }

    @Override
    public VehicleResponse updateVehicle(VehicleRequest request) {
        return null;
    }

    public  Optional<Vehicle> validationVehicle(Long idVehicle){
        Optional<Vehicle> vehicle = vehicleRepository.findById(idVehicle);
        if(vehicle.isEmpty())
            throw  new HttpException("Vehiculo no encontrado!", HttpStatus.NOT_FOUND);
        return vehicle;
    }

}
