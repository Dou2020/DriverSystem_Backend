package com.DriverSystem_Back.modules.vehiclevisit;
import com.DriverSystem_Back.exception.HttpException;
import com.DriverSystem_Back.modules.user.UserService;
import com.DriverSystem_Back.modules.vehicle.VehicleService;
import com.DriverSystem_Back.modules.vehiclevisit.dto.VehicleVisitRequest;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.xml.crypto.Data;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.Optional;

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

    @Override
    public VehicleVisit saveVehicleVisit(VehicleVisitRequest vehicleVisit) {
        this.userService.validateUser(vehicleVisit.customerId());
        this.vehicleService.validationVehicle(vehicleVisit.vehicleId());
        VehicleVisit vehicleVisit1=  modelMapper.map(vehicleVisit, VehicleVisit.class);
        vehicleVisit1.setArrivedAt(OffsetDateTime.now(ZoneOffset.of("-06:00")));
        return this.vehicleVisitRepository.save(vehicleVisit1);
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
