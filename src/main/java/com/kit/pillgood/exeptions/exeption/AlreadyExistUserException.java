package com.kit.pillgood.exeptions.exeption;

import com.kit.pillgood.exeptions.exeption.superExeption.AlreadyExistException;
import org.springframework.http.HttpStatus;

public class AlreadyExistUserException extends AlreadyExistException {
    public AlreadyExistUserException() {
        super("이미 등록된 유저 입니다.", HttpStatus.BAD_REQUEST);
    }
}
