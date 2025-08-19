package com.DriverSystem_Back.modules.vehiclemake;

import com.DriverSystem_Back.exception.HttpException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service
public class VehicleMakeService implements IVehicleMakeService{
    @Autowired
    private   VehicleMakeRepository vehicleMakeRepository;

    @Override
    public VehicleMake save(VehicleMakeRequest request) {
        Optional<VehicleMake> optional = this.vehicleMakeRepository.findByName(request.name());
        if(optional.isPresent())
            throw new HttpException("Esta marca ya esta registrado!", HttpStatus.UNPROCESSABLE_ENTITY);
        VehicleMake vehicleMake = new VehicleMake();
        vehicleMake.setName(request.name());
        return this.vehicleMakeRepository.save(vehicleMake);
    }

    @Override
    public void delete(Long vehicleId) {
        Optional<VehicleMake> optional = this.vehicleMakeRepository.findById(vehicleId);
        if (optional.isEmpty())
            throw new HttpException("La marca no existe!", HttpStatus.NOT_FOUND);
        this.vehicleMakeRepository.deleteById(vehicleId);
    }

    @Override
    public VehicleMake update(VehicleMakeRequest request) {
        Optional<VehicleMake> optional = this.vehicleMakeRepository.findById(request.id());
        if (optional.isEmpty())
            throw new HttpException("La marca no existe!", HttpStatus.NOT_FOUND);

        VehicleMake vehicleMake = optional.get();
        vehicleMake.setName(vehicleMake.getName());
        return this.vehicleMakeRepository.save(vehicleMake);
    }

    @Override
    public VehicleMake getVehicleMakeById(Long id) {
        Optional<VehicleMake> optional = this.vehicleMakeRepository.findById(id);
        if (optional.isEmpty()) {
            throw new HttpException("La marca no existe!", HttpStatus.NOT_FOUND);
        }
        return  this.vehicleMakeRepository.getReferenceById(id);
    }

    @Override
    public List<VehicleMake> getAllVehicleMakes() {
        return this.vehicleMakeRepository.findAll();
    }

}
