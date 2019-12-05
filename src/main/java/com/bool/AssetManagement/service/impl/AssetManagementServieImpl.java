package com.bool.AssetManagement.service.impl;

import com.bool.AssetManagement.domain.AssetHistory;
import com.bool.AssetManagement.exceptions.VehicleAlreadyExistsException;
import com.bool.AssetManagement.exceptions.VehicleNotFoundException;
import com.bool.AssetManagement.repository.VehicleRepository;
import com.bool.AssetManagement.service.AssetManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AssetManagementServieImpl implements AssetManagementService {

    private VehicleRepository vehicleRepository;

    @Autowired
    public AssetManagementServieImpl(VehicleRepository vehicleRepository){
       this.vehicleRepository= vehicleRepository;
    }

    List list = new ArrayList();

    @Override
    public AssetHistory saveVehicle(AssetHistory assetHistory) throws VehicleAlreadyExistsException {
        if(vehicleRepository.existsById(assetHistory.getId())){
            throw new VehicleAlreadyExistsException("Vehicle Already Exists");
        }
        AssetHistory savedAssetHistory = vehicleRepository.save(assetHistory);
        if(savedAssetHistory == null){
            throw new VehicleAlreadyExistsException("Vehicle Already Exists");
        }
        return savedAssetHistory;
    }

    @Override
    public List getAllVehicles(){
        List vehicleList = vehicleRepository.findAll();
        return vehicleList;
    }

    @Override
    public AssetHistory updateVehicle(AssetHistory assetHistory) throws VehicleNotFoundException {
        if(!(vehicleRepository.existsById(assetHistory.getId()))){
            throw new VehicleNotFoundException("Cant Update. Vehicle Not Found");
        }
        return vehicleRepository.save(assetHistory);
    }

    @Override
    public boolean deleteVehicle(int no) throws VehicleNotFoundException{
        if(!(vehicleRepository.existsById(no))){
            throw new VehicleNotFoundException("Cant Delete. Vehicle Not Found");
        }
        vehicleRepository.deleteById(no);
        return true;
    }


    @Override
    public String getBatteryOfVehicle(int no) throws VehicleNotFoundException{
        if(!(vehicleRepository.existsById(no))){
            throw new VehicleNotFoundException("Cant retrive battery percentage. Vehicle Not Found");
        }
        final String[] battery = {null};
       Optional<AssetHistory> vehicle = vehicleRepository.findById(no);
       vehicle.ifPresent(f -> {battery[0]= String.valueOf(f.getCharge());});
       return battery[0];
    }

    @Override
    public String getCommentsOnVehicle(int no) throws VehicleNotFoundException{
        if(!(vehicleRepository.existsById(no))){
            throw new VehicleNotFoundException("Cant retrieve Comments. Vehicle Not Found");
        }
        final String[] status = {null};
       Optional<AssetHistory> vehicle=  vehicleRepository.findById(no);
       vehicle.ifPresent((f -> {status[0] = f.getFeedbackOrComments();}));
        return status[0];
    }

    @Override
    public String getUsername(int rideCount,int no) throws VehicleNotFoundException{
        if(!(vehicleRepository.existsById(no))){
            throw new VehicleNotFoundException("Cant retrieve UserName. Vehicle Not Found");
        }
        final String[] status = {null};
        Optional<AssetHistory> vehicle= Optional.ofNullable(vehicleRepository.rideOfVehicle(no, rideCount));
        vehicle.ifPresent((f -> {status[0] = f.getUsername();}));
        return status[0];
//        return "something";
    }

    @Override
    public String getRideCount(int no) throws VehicleNotFoundException{
        if(!(vehicleRepository.existsById(no))){
            throw new VehicleNotFoundException("Cant retrieve  RideCount. Vehicle Not Found");
        }
        final String[] status = {null};
        Optional<AssetHistory> vehicle=  vehicleRepository.findById(no);
        vehicle.ifPresent((f -> {status[0] = String.valueOf(f.getRideCount());}));
        return status[0];
    }

    @Override
    public String  getInitMeterReading(int rideCount, int no) throws VehicleNotFoundException {
        if(!(vehicleRepository.existsById(no))){
            throw new VehicleNotFoundException("Cant get init Meter Reading. Vehicle Not Found");
        }
        final String[] status = {null};
        Optional<AssetHistory> vehicle= Optional.ofNullable(vehicleRepository.rideOfVehicle(no, rideCount));
        vehicle.ifPresent((f -> {status[0] = String.valueOf(f.getInitMeterReading());}));
        return status[0];
    }

    @Override
    public String getFinalMeterReading(int rideCount, int no) throws VehicleNotFoundException{
        if(!(vehicleRepository.existsById(no))){
            throw new VehicleNotFoundException("Cant retrieve Final meter Reading. Vehicle Not Found");
        }
        final String[] status = {null};
        Optional<AssetHistory> vehicle= Optional.ofNullable(vehicleRepository.rideOfVehicle(no, rideCount));
        vehicle.ifPresent((f -> {status[0] = String.valueOf(f.getFinalMeterReading());}));
        return status[0];
    }

    @Override
    public String getInitTime(int rideCount, int no) throws VehicleNotFoundException {
        if(!(vehicleRepository.existsById(no))){
            throw new VehicleNotFoundException("Cant retrieve Init. Vehicle Not Found");
        }
        final String[] status = {null};
        Optional<AssetHistory> vehicle= Optional.ofNullable(vehicleRepository.rideOfVehicle(no, rideCount));
        vehicle.ifPresent((f -> {status[0] = String.valueOf(f.getInitTime());}));
        return status[0];
    }
//
    @Override
    public String getDropTime(int rideCount, int no) throws VehicleNotFoundException{
        if(!(vehicleRepository.existsById(no))){
            throw new VehicleNotFoundException("Cant retrieve drop Time. Vehicle Not Found");
        }
        final String[] status = {null};
        Optional<AssetHistory> vehicle= Optional.ofNullable(vehicleRepository.rideOfVehicle(no, rideCount));
        vehicle.ifPresent((f -> {status[0] = String.valueOf(f.getDropTime());}));
        return status[0];
    }


    @Override
    public String getStation(int rideCount, int no) throws VehicleNotFoundException{
        if(!(vehicleRepository.existsById(no))){
            throw new VehicleNotFoundException("Cant Update. Vehicle Not Found");
        }
        final String[] status = {null};
        Optional<AssetHistory> vehicle= Optional.ofNullable(vehicleRepository.rideOfVehicle(no, rideCount));
        vehicle.ifPresent((f -> {status[0] = String.valueOf(f.getStation());}));
        return status[0];
    }



}
