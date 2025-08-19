package com.DriverSystem_Back.modules.vehiclemake;

import java.util.List;

public interface IVehicleMakeService {
    public  VehicleMake save(VehicleMakeRequest vehicleMake);
    public  void delete(Long vehicleId);
    public  VehicleMake update(VehicleMakeRequest id);
    public  VehicleMake getVehicleMakeById(Long id);
    public List<VehicleMake> getAllVehicleMakes();
}
