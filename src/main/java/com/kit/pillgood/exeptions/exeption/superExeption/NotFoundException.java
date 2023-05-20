package com.kit.pillgood.exeptions.exeption.superExeption;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class NotFoundException extends Exception{
    private final HttpStatus httpStatus;
    public NotFoundException(String message, HttpStatus httpStatus){
        super(message);
        this.httpStatus = httpStatus;

    }
}
