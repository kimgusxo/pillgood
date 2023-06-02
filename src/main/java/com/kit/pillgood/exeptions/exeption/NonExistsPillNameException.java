package com.kit.pillgood.exeptions.exeption;

import com.kit.pillgood.exeptions.exeption.superExeption.NotExistsException;
import org.springframework.http.HttpStatus;

public class NonExistsPillNameException extends NotExistsException {
    public NonExistsPillNameException(){
        super("PillName이 존재하지 않습니다. ", HttpStatus.NOT_FOUND);
    }
}
