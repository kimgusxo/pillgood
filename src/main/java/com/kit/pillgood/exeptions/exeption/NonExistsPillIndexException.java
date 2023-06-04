package com.kit.pillgood.exeptions.exeption;

import com.kit.pillgood.exeptions.exeption.superExeption.NotExistsException;
import org.springframework.http.HttpStatus;

public class NonExistsPillIndexException extends NotExistsException {
    public NonExistsPillIndexException(){
        super("등록되지 않은 PillIndex 입니다.", HttpStatus.NOT_FOUND);
    }
}
