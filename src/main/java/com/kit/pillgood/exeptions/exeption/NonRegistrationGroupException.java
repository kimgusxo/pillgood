package com.kit.pillgood.exeptions.exeption;

import com.kit.pillgood.exeptions.exeption.superExeption.NonRegistrationException;
import org.springframework.http.HttpStatus;

public class NonRegistrationGroupException extends NonRegistrationException {
    public NonRegistrationGroupException(){
        super("등록되지 않은 그룹원 입니다.", HttpStatus.UNAUTHORIZED);
    }
}
