package com.bool.AssetManagement.service;

import com.bool.AssetManagement.domain.Vehicle;
import com.bool.AssetManagement.exceptions.VehicleAlreadyExistsException;
import com.bool.AssetManagement.exceptions.VehicleNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
@Service
public interface AssetManagementService {
     Vehicle saveVehicle(Vehicle vehicle ) throws VehicleAlreadyExistsException;
     List getAllVehicles() ;
     Vehicle updateVehicle(Vehicle vehicle) throws VehicleNotFoundException;
     boolean deleteVehicle(int no)  throws VehicleNotFoundException;
     String getStatusOfVehicle(int no) throws VehicleNotFoundException;
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
