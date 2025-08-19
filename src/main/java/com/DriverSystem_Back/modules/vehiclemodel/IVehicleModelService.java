package com.DriverSystem_Back.modules.vehiclemodel;

import com.DriverSystem_Back.modules.vehiclemodel.dto.VehicleModelRequest;

import java.util.List;

public interface IVehicleModelService {
    public VehicleModel findByMakeId(Long makeId);
    public List<VehicleModel> findAll();
    public VehicleModel save(VehicleModelRequest vehicleModel);
    public VehicleModel update(VehicleModelRequest vehicleModel);
    public void deleteVehicleModel(Long id);
}
