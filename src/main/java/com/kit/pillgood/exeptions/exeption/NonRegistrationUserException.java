package com.kit.pillgood.exeptions.exeption;

import com.kit.pillgood.exeptions.exeption.superExeption.NonRegistrationException;
import org.springframework.http.HttpStatus;

public class NonRegistrationUserException extends NonRegistrationException {
    public NonRegistrationUserException(){
        super("등록되지 않은 유저 입니다.", HttpStatus.UNAUTHORIZED);
    }
}
