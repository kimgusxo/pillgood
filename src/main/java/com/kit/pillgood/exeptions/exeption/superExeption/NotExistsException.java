package com.kit.pillgood.exeptions.exeption.superExeption;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class NotExistsException extends Exception{
    private final HttpStatus httpStatus;
    public NotExistsException(String message, HttpStatus httpStatus){
        super(message);
        this.httpStatus = httpStatus;

    }
}
