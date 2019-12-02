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
public class Vehicle {

     @Id
     int id;
     int charge;
     String username;
     int initMeterReading;
     int finalMeterReading;
     int rideCount;
     String initTime;
     String dropTime;
     int totalDistance;
     String feedbackOrComments;
     String status;
     String station;

}
