package com.bool.AssetManagement.exceptions;

public class BookingNotFoundException extends Exception {
    private String message;
    public BookingNotFoundException(){}
    public BookingNotFoundException(String message){
        super(message);
        this.message= message;
    }
}
