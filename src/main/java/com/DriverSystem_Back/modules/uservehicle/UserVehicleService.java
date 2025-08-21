package com.DriverSystem_Back.modules.uservehicle;

import com.DriverSystem_Back.exception.HttpException;
import com.DriverSystem_Back.modules.user.User;
import com.DriverSystem_Back.modules.uservehicle.dto.UserVehicleRequest;
import com.DriverSystem_Back.modules.uservehicle.dto.UserVehicleResponde;
import com.DriverSystem_Back.modules.vehicle.Vehicle;
import com.DriverSystem_Back.modules.vehicle.VehicleRepository;
import com.DriverSystem_Back.modules.vehicle.view.VehicleResponse;
import com.DriverSystem_Back.modules.vehicle.view.VehicleResponseRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserVehicleService {
    @Autowired
    private  UserVehicleRepository userVehicleRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private VehicleResponseRepository vehicleResponseRepository;



    public  UserVehicleResponde saveUserVehicle(UserVehicleRequest request){
        UserVehicle userVehicle = new UserVehicle();
        userVehicle.setUserId(request.userId());
        userVehicle.setVehicleId(request.vehicleId());
        try {
         userVehicle =  userVehicleRepository.save(userVehicle);
        }catch (Exception e){
            throw new HttpException("Ya existe  este registro", HttpStatus.UNPROCESSABLE_ENTITY);
        }
        VehicleResponse vehicle = this.vehicleResponseRepository.findById(userVehicle.getVehicleId()).get();
        return new UserVehicleResponde(vehicle,userVehicle.getId());

    }

    public List<UserVehicleResponde> getAllVehicles(Long userId) {
        List<UserVehicle> userVehicles = this.userVehicleRepository.findAllByUserId(userId);
        List<UserVehicleResponde> list = new ArrayList<>();

        for (UserVehicle uv : userVehicles) {
            VehicleResponse vehicle = this.vehicleResponseRepository.findById(uv.getVehicleId()).get();
            if (vehicle != null) {
                list.add(new UserVehicleResponde(vehicle,uv.getId()));
            }
        }

        return list;
    }


    public void  deleteUserVehicle(Long id){
        UserVehicle userVehicle = this.userVehicleRepository.findById(id).orElseThrow(()->new HttpException("No existe  el registro",HttpStatus.NOT_FOUND));

        this.userVehicleRepository.deleteById(id);
    }

    public UserVehicleResponde  updateUserVehicle(UserVehicleRequest request){
        UserVehicle userVehicle = this.userVehicleRepository.findById(request.id()).orElseThrow(()->new HttpException("No existe  el registro",HttpStatus.NOT_FOUND));
        userVehicle.setVehicleId(request.vehicleId());
      //  userVehicle.setUserId(request.userId());
        this.userVehicleRepository.save(userVehicle);
        VehicleResponse vehicle = this.vehicleResponseRepository.findById(userVehicle.getVehicleId()).get();
        return new UserVehicleResponde(vehicle,userVehicle.getId());
    }


}
