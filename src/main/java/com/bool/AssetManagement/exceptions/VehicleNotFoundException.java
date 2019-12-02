package com.bool.AssetManagement.exceptions;

public class VehicleNotFoundException extends Exception {
    private String message;
    public VehicleNotFoundException(){}
    public VehicleNotFoundException(String message){
        super(message);
        this.message= message;
    }
}
