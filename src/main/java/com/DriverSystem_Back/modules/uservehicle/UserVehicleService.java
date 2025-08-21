package com.DriverSystem_Back.modules.uservehicle;

import com.DriverSystem_Back.modules.uservehicle.dto.UserVehicleRequest;
import com.DriverSystem_Back.modules.vehicle.Vehicle;
import com.DriverSystem_Back.modules.vehicle.VehicleRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserVehicleService {
    @Autowired
    private  UserVehicleRepository userVehicleRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private VehicleRepository vehicleRepository;

    public  void saveUserVehicle(UserVehicleRequest request){
         UserVehicle userVehicle = modelMapper.map(request,UserVehicle.class);
            this.userVehicleRepository.save(userVehicle);
    }


}
