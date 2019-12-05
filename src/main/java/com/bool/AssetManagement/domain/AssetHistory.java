package com.bool.AssetManagement.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
//@Entity
public class AssetHistory {

     @Id
     int id; //regno
     int charge; //console //charge at the end of ride
     String username;  //booking // username might be same
     int initMeterReading; //booking
     int finalMeterReading; //booking
     int rideCount; //
     String initTime; //booking
     String dropTime; //booking
     int totalDistance; //
     String feedbackOrComments; //
     //String status; //booking
     String station; //booking

}
