package com.DriverSystem_Back.modules.vehiclemodel;

import com.DriverSystem_Back.exception.HttpException;
import com.DriverSystem_Back.modules.vehiclemake.VehicleMake;
import com.DriverSystem_Back.modules.vehiclemake.VehicleMakeRepository;
import com.DriverSystem_Back.modules.vehiclemodel.dto.VehicleModelRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VehicleModelService  implements  IVehicleModelService{
    @Autowired
    private VehicleModelRepository vehicleRepository;
    @Autowired
    private VehicleMakeRepository vehicleMakeRepository;

    @Override
    public VehicleModel findByMakeId(Long modeld) {
        this.validate(modeld);
        return this.vehicleRepository.getReferenceById(modeld);
    }

    @Override
    public List<VehicleModel> findAll() {
        return this.vehicleRepository.findAll();
    }

    @Override
    public VehicleModel save(VehicleModelRequest request) {
        Optional<VehicleModel> optional = this.vehicleRepository.findByName(request.name());
        if(optional.isPresent())
            throw new HttpException("Esta marca ya esta registrado!", HttpStatus.UNPROCESSABLE_ENTITY);
        Optional<VehicleMake> vehicleMakeOptional = vehicleMakeRepository.findById(request.makeId());
        if(vehicleMakeOptional.isEmpty())
            throw  new HttpException("No existe la marca!", HttpStatus.NOT_FOUND);

       VehicleModel vehicleModel = new VehicleModel();
       vehicleModel.setMakeId(request.makeId());
       vehicleModel.setName(request.name());
        return  this.vehicleRepository.save(vehicleModel);
    }

    @Override
    public VehicleModel update(VehicleModelRequest vehicleModel) {
        VehicleModel vehicleModelUpdate = this.validate(vehicleModel.id()).get();

        Optional<VehicleMake> vehicleMakeOptional = vehicleMakeRepository.findById(vehicleModel.id());
        if(vehicleMakeOptional.isEmpty())
            throw  new HttpException("No existe la marca!", HttpStatus.NOT_FOUND);

        vehicleModelUpdate.setName(vehicleModel.name());
        vehicleModelUpdate.setMakeId(vehicleModel.id());
        return   this.vehicleRepository.save(vehicleModelUpdate);
    }


    @Override
    public void deleteVehicleModel(Long id) {
        this.validate(id);
        this.vehicleRepository.deleteById(id);
    }

    public Optional<VehicleModel> validate(Long id){
        Optional<VehicleModel> vehicleModelOptional = vehicleRepository.findById(id);
        if(vehicleModelOptional.isEmpty())
            throw  new HttpException("No existe el modelo!", HttpStatus.NOT_FOUND);
        return vehicleModelOptional;
    }
}
