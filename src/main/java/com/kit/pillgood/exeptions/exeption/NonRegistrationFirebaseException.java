package com.kit.pillgood.exeptions.exeption;

import com.kit.pillgood.exeptions.exeption.superExeption.NonRegistrationException;
import org.springframework.http.HttpStatus;

public class NonRegistrationFirebaseException extends NonRegistrationException {
    public NonRegistrationFirebaseException(){
        super("Firebase에 등록되지 않은 유저 입니다.", HttpStatus.UNAUTHORIZED);
    }
}
