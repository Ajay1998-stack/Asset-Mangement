package com.bool.AssetManagement.exceptions;

public class BookingAlreadyExistsException extends Exception {
    private String message;
    public BookingAlreadyExistsException(){}
    public BookingAlreadyExistsException(String message){
        super(message);
        this.message= message;
    }
}
