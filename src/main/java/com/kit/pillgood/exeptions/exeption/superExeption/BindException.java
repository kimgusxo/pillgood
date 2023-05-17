package com.kit.pillgood.exeptions.exeption.superExeption;

import org.springframework.http.HttpStatus;

public class BindException extends Exception{
    private HttpStatus httpStatus;
    public BindException(String message){
        super(message);
        this.httpStatus = HttpStatus.BAD_REQUEST;
    }

}
