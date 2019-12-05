package com.bool.AssetManagement.domain;

import org.springframework.data.annotation.Id;

public class RideCount {
    @Id
    private int VehicleNo;
    private int rideCount;
}
