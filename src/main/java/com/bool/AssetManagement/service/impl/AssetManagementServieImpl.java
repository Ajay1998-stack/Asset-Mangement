package com.bool.AssetManagement.service.impl;

import com.bool.AssetManagement.domain.AssetHistory;
import com.bool.AssetManagement.exceptions.BookingAlreadyExistsException;
import com.bool.AssetManagement.exceptions.BookingNotFoundException;
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
    public AssetHistory saveVehicle(AssetHistory assetHistory) throws BookingAlreadyExistsException {
        if(vehicleRepository.existsById(assetHistory.getId())){
            throw new BookingAlreadyExistsException("Booking Already Exists");
        }
        AssetHistory savedAssetHistory = vehicleRepository.save(assetHistory);
        if(savedAssetHistory == null){
            throw new BookingAlreadyExistsException("Boooking Already Exists");
        }
        return savedAssetHistory;
    }

    @Override
    public List getAllVehicles(){
        List vehicleList = vehicleRepository.findAll();
        return vehicleList;
    }

    @Override
    public AssetHistory updateVehicle(AssetHistory assetHistory) throws BookingNotFoundException {
        if(!(vehicleRepository.existsById(assetHistory.getId()))){
            throw new BookingNotFoundException("Cant Update. Vehicle Not Found");
        }
        return vehicleRepository.save(assetHistory);
    }

    @Override
    public boolean deleteVehicle(int no) throws BookingNotFoundException{
        if(!(vehicleRepository.existsById(no))){
            throw new BookingNotFoundException("Cant Delete. Vehicle Not Found");
        }
        vehicleRepository.deleteById(no);
        return true;
    }

    @Override
    public String getUsername(int rideCount,int no) throws BookingNotFoundException{
        if(!(vehicleRepository.existsById(no))){
            throw new BookingNotFoundException("Cant retrieve UserName. Vehicle Not Found");
        }
        final String[] status = {null};
        Optional<AssetHistory> vehicle= Optional.ofNullable(vehicleRepository.rideOfVehicle(no, rideCount));
        vehicle.ifPresent((f -> {status[0] = f.getUsername();}));
        return status[0];
//        return "something";
    }

    @Override
    public String getRideCount(int no) throws BookingNotFoundException{
        if(!(vehicleRepository.existsById(no))){
            throw new BookingNotFoundException("Cant retrieve  RideCount. Vehicle Not Found");
        }
        final String[] status = {null};
        Optional<AssetHistory> vehicle=  vehicleRepository.findById(no);
        vehicle.ifPresent((f -> {status[0] = String.valueOf(f.getRideCount());}));
        return status[0];
    }
}
