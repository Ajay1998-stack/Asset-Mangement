package com.bool.AssetManagement.domain;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingObject {
    private String vehicle_id ;
    private String user_id ;
    private int initial_meter_reading;
  //  private int final_meter_reading; //end
    private String start_station;
   // private String end_station; //end
    private String starttime;
   private String comments; //end
  //  private String endtime; //end
    private int rideCount;
  private String vehicle_status; //unneccesssary

}
