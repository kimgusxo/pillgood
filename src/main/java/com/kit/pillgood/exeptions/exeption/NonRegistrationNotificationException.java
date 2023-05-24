package com.kit.pillgood.exeptions.exeption;

import com.kit.pillgood.exeptions.exeption.superExeption.NonRegistrationException;
import org.springframework.http.HttpStatus;

public class NonRegistrationNotificationException extends NonRegistrationException {
    public NonRegistrationNotificationException(){
        super("존재하지 않은 알림 입니다.", HttpStatus.NOT_FOUND);
    }
}
