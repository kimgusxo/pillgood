package com.kit.pillgood.exeptions.exeption.superExeption;

import org.springframework.http.HttpStatus;

public class NonRegistrationException extends Exception{
    private HttpStatus httpStatus;
    public NonRegistrationException(String message, HttpStatus httpStatus){
        super(message);
        this.httpStatus = httpStatus;
    }

}
