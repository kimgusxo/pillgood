package com.kit.pillgood.exeptions.exeption;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class TransactionFailedException extends RuntimeException{

    private final HttpStatus httpStatus;
    public TransactionFailedException(){
        super("Transaction failed");
        this.httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
    }

}
