package com.bool.AssetManagement.service;

import com.bool.AssetManagement.domain.BookingObject;
import com.bool.AssetManagement.domain.Vehicle;
import com.bool.AssetManagement.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AssetManagementServieImpl implements AssetManagementService{

    private VehicleRepository vehicleRepository;


   @Autowired
    public AssetManagementServieImpl(VehicleRepository vehicleRepository){
       this.vehicleRepository= vehicleRepository;
    }

    List list = new ArrayList();

    @Override
    public Vehicle saveVehicle(Vehicle vehicle){
        Vehicle savedVehicle = vehicleRepository.save(vehicle);
        return savedVehicle;
    }

    @Override
    public List getAllVehicles() {
        List vehicleList = vehicleRepository.findAll();
        return vehicleList;
    }

    @Override
    public Vehicle updateVehicle(Vehicle vehicle) {
        return vehicleRepository.save(vehicle);
    }

    @Override
    public boolean deleteVehicle(int no) {
        vehicleRepository.deleteById(no);
        return true;
    }

    @Override
    public String getStatusOfVehicle(int no) {
        final String[] status = {null};
        Optional<Vehicle> vehicle1 = vehicleRepository.findById(no);
        vehicle1.ifPresent(f -> { status[0] = f.getStatus();});
        return status[0];
     }


    @Override
    public String getBatteryOfVehicle(int no) {
        final String[] battery = {null};
       Optional<Vehicle> vehicle = vehicleRepository.findById(no);
       vehicle.ifPresent(f -> {battery[0]= String.valueOf(f.getCharge());});
       return battery[0];
    }

    @Override
    public String getCommentsOnVehicle(int no) {
        final String[] status = {null};
       Optional<Vehicle> vehicle=  vehicleRepository.findById(no);
       vehicle.ifPresent((f -> {status[0] = f.getFeedbackOrComments();}));
        return status[0];
    }

    @Override
    public String getUsername(int rideCount,int no) {
        final String[] status = {null};
        Optional<Vehicle> vehicle= Optional.ofNullable(vehicleRepository.rideOfVehicle(no, rideCount));
        vehicle.ifPresent((f -> {status[0] = f.getUsername();}));
        return status[0];
//        return "something";
    }

    @Override
    public String getRideCount(int no) {
        final String[] status = {null};
        Optional<Vehicle> vehicle=  vehicleRepository.findById(no);
        vehicle.ifPresent((f -> {status[0] = String.valueOf(f.getRideCount());}));
        return status[0];
    }

    @Override
    public String  getInitMeterReading(int rideCount, int no) {
        final String[] status = {null};
        Optional<Vehicle> vehicle= Optional.ofNullable(vehicleRepository.rideOfVehicle(no, rideCount));
        vehicle.ifPresent((f -> {status[0] = String.valueOf(f.getInitMeterReading());}));
        return status[0];
    }

    @Override
    public String getFinalMeterReading(int rideCount, int no) {
        final String[] status = {null};
        Optional<Vehicle> vehicle= Optional.ofNullable(vehicleRepository.rideOfVehicle(no, rideCount));
        vehicle.ifPresent((f -> {status[0] = String.valueOf(f.getFinalMeterReading());}));
        return status[0];
    }

    @Override
    public String getInitTime(int rideCount, int no) {
        final String[] status = {null};
        Optional<Vehicle> vehicle= Optional.ofNullable(vehicleRepository.rideOfVehicle(no, rideCount));
        vehicle.ifPresent((f -> {status[0] = String.valueOf(f.getInitTime());}));
        return status[0];
    }
//
    @Override
    public String getDropTime(int rideCount, int no) {
        final String[] status = {null};
        Optional<Vehicle> vehicle= Optional.ofNullable(vehicleRepository.rideOfVehicle(no, rideCount));
        vehicle.ifPresent((f -> {status[0] = String.valueOf(f.getDropTime());}));
        return status[0];
    }


    @Override
    public String getStation(int rideCount, int no) {
        final String[] status = {null};
        Optional<Vehicle> vehicle= Optional.ofNullable(vehicleRepository.rideOfVehicle(no, rideCount));
        vehicle.ifPresent((f -> {status[0] = String.valueOf(f.getStation());}));
        return status[0];
    }

    @KafkaListener(topics = "Kafka_start_ride", groupId = "group_json",containerFactory = "userKafkaListenerFactory")
    public void KafkaCons(BookingObject bookingObject){
        list.add(bookingObject);
        System.out.println("Consumed Message :"+bookingObject);
        System.out.println(list);
    }
}
