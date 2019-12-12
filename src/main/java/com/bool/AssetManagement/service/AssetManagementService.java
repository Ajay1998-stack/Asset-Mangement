package com.bool.AssetManagement.service;

import com.bool.AssetManagement.domain.AssetHistory;
import com.bool.AssetManagement.exceptions.BookingAlreadyExistsException;
import com.bool.AssetManagement.exceptions.BookingNotFoundException;
import com.bool.AssetManagement.exceptions.VehicleNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public interface AssetManagementService {
     AssetHistory saveVehicle(AssetHistory assetHistory) throws BookingAlreadyExistsException;
     List getAllVehicles() ;
     AssetHistory updateVehicle(AssetHistory assetHistory) throws BookingNotFoundException;
     boolean deleteVehicle(UUID bookingId)  throws BookingNotFoundException;
     String getUsername(long rideCount,UUID bookingId) throws BookingNotFoundException;
     String getRideCount(UUID bookingId) throws BookingNotFoundException;
}
