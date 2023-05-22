package com.kit.pillgood.exeptions.exeption.superExeption;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class EtcFirebaseException extends Exception{
    private final HttpStatus httpStatus;
    public EtcFirebaseException(){
        super("firebase API err");
        this.httpStatus = HttpStatus.NOT_IMPLEMENTED;
    }
}
