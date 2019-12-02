package com.bool.AssetManagement.service;

import com.bool.AssetManagement.domain.Vehicle;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
@Service
public interface AssetManagementService {
     Vehicle saveVehicle(Vehicle vehicle);
     List getAllVehicles();
     Vehicle updateVehicle(Vehicle vehicle);
     boolean deleteVehicle(int no);
     String getStatusOfVehicle(int no);
     String getBatteryOfVehicle(int no);
     String getCommentsOnVehicle(int no);
     String getUsername(int rideCount,int no);
     String getRideCount(int no);
     String getInitMeterReading(int rideCount,int no);
     String getFinalMeterReading(int rideCount, int no);
     String getInitTime(int rideCount, int no);
     String getDropTime(int rideCount, int no);
//     void KafkaCons(Vehicle vehicle);
     String getStation(int rideCount, int no);

}
