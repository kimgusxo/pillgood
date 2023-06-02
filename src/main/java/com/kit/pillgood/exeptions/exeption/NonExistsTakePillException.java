package com.kit.pillgood.exeptions.exeption;

import com.kit.pillgood.exeptions.exeption.superExeption.NotExistsException;
import org.springframework.http.HttpStatus;

public class NonExistsTakePillException extends NotExistsException {
    public NonExistsTakePillException(){
        super("TakePill이 존재하지 않습니다.", HttpStatus.NOT_FOUND);
    }
}
