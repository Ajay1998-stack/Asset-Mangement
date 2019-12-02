package com.bool.AssetManagement.exceptions;

public class VehicleAlreadyExistsException extends Exception {
    private String message;
    public VehicleAlreadyExistsException(){}
    public VehicleAlreadyExistsException(String message){
        super(message);
        this.message= message;
    }
}
