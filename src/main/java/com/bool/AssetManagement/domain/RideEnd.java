package com.bool.AssetManagement.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RideEnd {
    private java.util.UUID booking_id;
    private String vehicle_id ;
    private String user_id ;
    private int initial_meter_reading;
    private int final_meter_reading;
    private Timestamp starttime;
    private Timestamp endtime;
    private String start_station;
    private String end_station;
    private String vehicle_status;
    private long duration;
    private String status_of_ride;
    private long pause_duration;
    private double fare;
    private String comments;
}
