package com.DriverSystem_Back.modules.vehiclevisit;

import com.DriverSystem_Back.modules.vehiclemodel.dto.VehicleModelRequest;
import com.DriverSystem_Back.modules.vehiclevisit.dto.VehicleVisitRequest;

public interface IVehicleVisitService {
    public VehicleVisit  saveVehicleVisit(VehicleVisitRequest vehicleVisit);
    public VehicleVisit  updateVehicleVisit(VehicleVisitRequest vehicleVisit);
    public void  deleteVehicleVisit(Long vehicleVisit);
    public VehicleVisit  getVehicleVisitById(Long id);
    public  void updataPartedAt(Long id);
}
