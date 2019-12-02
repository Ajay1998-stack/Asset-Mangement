package com.bool.AssetManagement.controller;

//import com.bool.AssetManagement.domain.KakfaObject;
import com.bool.AssetManagement.domain.Vehicle;
import com.bool.AssetManagement.exceptions.VehicleAlreadyExistsException;
import com.bool.AssetManagement.exceptions.VehicleNotFoundException;
import com.bool.AssetManagement.service.AssetManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(value = "api/v1")
public class AssetController {
    private AssetManagementService assetManagementService;
    @Autowired
    public AssetController(AssetManagementService  assetManagementService){
        this.assetManagementService = assetManagementService;
    }


    @PostMapping("asset")
    public ResponseEntity<?> saveVehicle(@RequestBody Vehicle vehicle){
        ResponseEntity responseEntity;
        try {
            assetManagementService.saveVehicle(vehicle);
            responseEntity = new ResponseEntity<Vehicle>(vehicle, HttpStatus.CREATED);
        }catch (VehicleAlreadyExistsException ex){
            responseEntity = new ResponseEntity<String>(ex.getMessage(),HttpStatus.CONFLICT);
        }
        return responseEntity;
    }

    @GetMapping("asset")
    public ResponseEntity<?> getAllVehicles(){
        return new ResponseEntity<List<Vehicle>>(assetManagementService.getAllVehicles(),HttpStatus.OK);

    }

    @PutMapping("assetUpdate")
    public  ResponseEntity<?> updateVehicle(@RequestBody Vehicle vehicle) {
       ResponseEntity responseEntity;
       try {
           assetManagementService.saveVehicle(assetManagementService.updateVehicle(vehicle));
           return new ResponseEntity<String>("successfully updated", HttpStatus.OK);
       }catch (Exception ex){
           responseEntity= new ResponseEntity<String>(ex.getMessage(),HttpStatus.CONFLICT);
       }
        return responseEntity;
    }

    @DeleteMapping("assetUpdate/{id}")
    public ResponseEntity<?> deleteVehicle(@PathVariable("id") int id){
    ResponseEntity responseEntity;
    try {
        assetManagementService.deleteVehicle(id);
        return new ResponseEntity<>(assetManagementService.getAllVehicles(), HttpStatus.OK);
    }catch (Exception ex){
        responseEntity = new ResponseEntity<String>(ex.getMessage(),HttpStatus.CONFLICT);
    }
        return responseEntity;
    }

    @GetMapping("status/{id}")
    public ResponseEntity<?> getStatusOfVehicle(@PathVariable("id") int id){
        ResponseEntity responseEntity;
        try {
            return new ResponseEntity<String>(assetManagementService.getStatusOfVehicle(id), HttpStatus.OK);
        }catch (Exception ex){
            responseEntity = new ResponseEntity<String>(ex.getMessage(),HttpStatus.CONFLICT);
        }
        return responseEntity;
        }

    @GetMapping("battery/{id}")
    public ResponseEntity<?> getBatteryOfVehicle(@PathVariable("id") int id){
        ResponseEntity responseEntity;
        try {
            return new ResponseEntity<String>(assetManagementService.getBatteryOfVehicle(id), HttpStatus.OK);
        }catch (Exception ex){
            responseEntity = new ResponseEntity<String>(ex.getMessage(),HttpStatus.CONFLICT);
        }
        return responseEntity;
    }


    @GetMapping("comments/{id}")
    public ResponseEntity<?> getCommentsOnVehicle(@PathVariable("id") int id){
        ResponseEntity responseEntity;
        try {
            return new ResponseEntity<String>(assetManagementService.getCommentsOnVehicle(id), HttpStatus.OK);
        }
        catch (Exception ex){
            responseEntity = new ResponseEntity<String>(ex.getMessage(),HttpStatus.CONFLICT);
        }
        return responseEntity;
    }

    @GetMapping("count/{id}")
    public ResponseEntity<?> getRideCountOfVehicle(@PathVariable("id") int id){
        ResponseEntity responseEntity;
        try {
            return new ResponseEntity<>(assetManagementService.getRideCount(id), HttpStatus.OK);
        }catch (Exception ex){
            responseEntity = new ResponseEntity<String>(ex.getMessage(),HttpStatus.CONFLICT);
        }
        return responseEntity;
    }

    @GetMapping("user/{id1}/{id2}")
    public ResponseEntity<?> getUsername(@PathVariable("id1") int id,@PathVariable("id2") int rideCount){
        ResponseEntity responseEntity;
        try {
            return new ResponseEntity<>(assetManagementService.getUsername(rideCount, id), HttpStatus.OK);
        }catch (Exception ex){
            responseEntity = new ResponseEntity<String>(ex.getMessage(),HttpStatus.CONFLICT);
        }
        return responseEntity;
    }

    @GetMapping("ReadingInit/{id1}/{id2}")
    public ResponseEntity<?> getInitMeterReading(@PathVariable("id1") int id,@PathVariable("id2") int rideCount){
        ResponseEntity responseEntity;
        try {
            return new ResponseEntity<>(assetManagementService.getInitMeterReading(rideCount, id), HttpStatus.OK);
        }catch (Exception ex){
            responseEntity = new ResponseEntity<String>(ex.getMessage(),HttpStatus.CONFLICT);
        }
        return responseEntity;
    }

    @GetMapping("ReadingDrop/{id1}/{id2}")
    public ResponseEntity<?> getFinalMeterReading(@PathVariable("id1") int id,@PathVariable("id2") int rideCount){
        ResponseEntity responseEntity;
        try {
            return new ResponseEntity<>(assetManagementService.getFinalMeterReading(rideCount, id), HttpStatus.OK);
        }catch (Exception ex){
            responseEntity = new ResponseEntity<String>(ex.getMessage(),HttpStatus.CONFLICT);
        }
        return responseEntity;
    }


    @GetMapping("TimeInit/{id1}/{id2}")
    public ResponseEntity<?> getInitTime(@PathVariable("id1") int id,@PathVariable("id2") int rideCount){
        ResponseEntity responseEntity;
        try{
        return new ResponseEntity<>(assetManagementService.getInitTime(rideCount,id),HttpStatus.OK);
        }catch (Exception ex){
            responseEntity = new ResponseEntity<String>(ex.getMessage(),HttpStatus.CONFLICT);
        }
        return responseEntity;
    }

    @GetMapping("TimeDrop/{id1}/{id2}")
    public ResponseEntity<?> getDropTime(@PathVariable("id1") int id,@PathVariable("id2") int rideCount){
        ResponseEntity responseEntity;
        try {
            return new ResponseEntity<>(assetManagementService.getDropTime(rideCount, id), HttpStatus.OK);
        }
        catch (Exception ex){
            responseEntity = new ResponseEntity<String>(ex.getMessage(),HttpStatus.CONFLICT);
        }
        return responseEntity;
    }

    @GetMapping("station/{id1}/{id2}")
    public ResponseEntity<?> getStation(@PathVariable("id1") int id,@PathVariable("id2") int rideCount){
        ResponseEntity responseEntity;
        try {
            return new ResponseEntity<>(assetManagementService.getStation(rideCount, id), HttpStatus.OK);
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
