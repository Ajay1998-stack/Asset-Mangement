package com.bool.AssetManagement.service;

import com.bool.AssetManagement.domain.AssetHistory;
import com.bool.AssetManagement.exceptions.VehicleAlreadyExistsException;
import com.bool.AssetManagement.exceptions.VehicleNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface AssetManagementService {
     AssetHistory saveVehicle(AssetHistory assetHistory) throws VehicleAlreadyExistsException;
     List getAllVehicles() ;
     AssetHistory updateVehicle(AssetHistory assetHistory) throws VehicleNotFoundException;
     boolean deleteVehicle(int no)  throws VehicleNotFoundException;
     String getBatteryOfVehicle(int no) throws VehicleNotFoundException;
     String getCommentsOnVehicle(int no) throws VehicleNotFoundException;
     String getUsername(int rideCount,int no) throws VehicleNotFoundException;
     String getRideCount(int no) throws VehicleNotFoundException;
     String getInitMeterReading(int rideCount,int no) throws VehicleNotFoundException;
     String getFinalMeterReading(int rideCount, int no) throws VehicleNotFoundException;
     String getInitTime(int rideCount, int no) throws VehicleNotFoundException;
     String getDropTime(int rideCount, int no) throws VehicleNotFoundException;
//     void KafkaCons(Vehicle vehicle);
     String getStation(int rideCount, int no) throws VehicleNotFoundException;

}
