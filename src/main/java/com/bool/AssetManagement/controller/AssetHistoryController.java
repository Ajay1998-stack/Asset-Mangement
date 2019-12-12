package com.bool.AssetManagement.controller;

//import com.bool.AssetManagement.domain.KakfaObject;

import com.bool.AssetManagement.domain.AdminObject;
import com.bool.AssetManagement.domain.RideStart;
import com.bool.AssetManagement.domain.AssetHistory;
import com.bool.AssetManagement.exceptions.BookingAlreadyExistsException;
import com.bool.AssetManagement.exceptions.VehicleAlreadyExistsException;
import com.bool.AssetManagement.service.AssetManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static java.lang.Integer.parseInt;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value = "api/v2")
public class AssetHistoryController {
    private AssetManagementService assetManagementService;
    @Autowired
    public AssetHistoryController(AssetManagementService  assetManagementService){
        this.assetManagementService = assetManagementService;
    }

    @Autowired
    private SimpMessagingTemplate template;


    @PostMapping("assetHis")
    public ResponseEntity<?> saveVehicle(@RequestBody AssetHistory assetHistory){
        ResponseEntity responseEntity;
        try {
            assetManagementService.saveVehicle(assetHistory);
            responseEntity = new ResponseEntity<AssetHistory>(assetHistory, HttpStatus.CREATED);
        }catch (BookingAlreadyExistsException ex){
            responseEntity = new ResponseEntity<String>(ex.getMessage(),HttpStatus.CONFLICT);
        }
        return responseEntity;
    }

    @GetMapping("assetHis")
    public ResponseEntity<?> getAllVehicles(){
        return new ResponseEntity<List<AssetHistory>>(assetManagementService.getAllVehicles(),HttpStatus.OK);

    }

    @PutMapping("assetUpdate")
    public  ResponseEntity<?> updateVehicle(@RequestBody AssetHistory assetHistory) {
       ResponseEntity responseEntity;
       try {
           assetManagementService.updateVehicle(assetHistory);
           return new ResponseEntity<String>("successfully updated", HttpStatus.OK);
       }catch (Exception ex){
           responseEntity= new ResponseEntity<String>(ex.getMessage(),HttpStatus.CONFLICT);
       }
        return responseEntity;
    }

    @DeleteMapping("assetHisUpdate/{id}")
    public ResponseEntity<?> deleteVehicle(@PathVariable("id") UUID id){
    ResponseEntity responseEntity;
    try {
        assetManagementService.deleteVehicle(id);
        return new ResponseEntity<>(assetManagementService.getAllVehicles(), HttpStatus.OK);
    }catch (Exception ex){
        responseEntity = new ResponseEntity<String>(ex.getMessage(),HttpStatus.CONFLICT);
    }
        return responseEntity;
    }


    @GetMapping("count/{id}")
    public ResponseEntity<?> getRideCountOfVehicle(@PathVariable("id") UUID id){
        ResponseEntity responseEntity;
        try {
            return new ResponseEntity<>(assetManagementService.getRideCount(id), HttpStatus.OK);
        }catch (Exception ex){
            responseEntity = new ResponseEntity<String>(ex.getMessage(),HttpStatus.CONFLICT);
        }
        return responseEntity;
    }

    @GetMapping("user/{id1}/{id2}")
    public ResponseEntity<?> getUsername(@PathVariable("id1") UUID id,@PathVariable("id2") long rideCount){
        ResponseEntity responseEntity;
        try {
            return new ResponseEntity<>(assetManagementService.getUsername(rideCount, id), HttpStatus.OK);
        }catch (Exception ex){
            responseEntity = new ResponseEntity<String>(ex.getMessage(),HttpStatus.CONFLICT);
        }
        return responseEntity;
    }




//    @Autowired
//    KafkaTemplate<String, KakfaObject> KafkaJsontemplate;
//
//    String TOPIC_NAME = "test";
//
//    @PostMapping(value = "/postItem",consumes = {"application/json"},produces = {"application/json"})
//    public ResponseEntity<String> postJsonMessage(@RequestBody Vehicle vehicle){
//        KakfaObject kakfaObject = new KakfaObject(vehicle.getNo(),vehicle.getRegno(),vehicle.getCharge(),vehicle.getInitMeterReading(),vehicle.getInitTime(),vehicle.getFeedbackOrComments());
//        KafkaJsontemplate.send(TOPIC_NAME,kakfaObject);
//        return new ResponseEntity<>("produced",HttpStatus.OK);
//    }
}
