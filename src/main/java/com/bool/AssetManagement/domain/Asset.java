package com.bool.AssetManagement.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Asset {
    @Id
    private String regNo;
    private int vehicleNo;
    private int charge; //end
    private int meterReading;
    private String feedbackOrComments;
    private String status; //available or unvailable
    private String station;
    private String photoUrl;

}
