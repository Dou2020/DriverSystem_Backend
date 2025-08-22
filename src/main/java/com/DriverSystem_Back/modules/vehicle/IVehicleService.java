package com.DriverSystem_Back.modules.vehicle;

import com.DriverSystem_Back.modules.vehicle.dto.VehicleRequest;
import com.DriverSystem_Back.modules.vehicle.view.VehicleResponse;

import java.util.List;

public interface IVehicleService {

    public VehicleResponse getVehicle(String plate);
    public VehicleResponse saveVehicle(VehicleRequest vehicle);
    public void deleteVehicle(Long idVehicle);
    public VehicleResponse updateVehicle( VehicleRequest vehicle);
    public List<VehicleResponse> findAllVehicle();
    public List<VehicleResponse> findUnassignedVehicles();
}
