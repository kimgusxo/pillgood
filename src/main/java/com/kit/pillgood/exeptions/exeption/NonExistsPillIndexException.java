package com.kit.pillgood.exeptions.exeption;

import com.kit.pillgood.exeptions.exeption.superExeption.NotExistsException;
import org.springframework.http.HttpStatus;

public class NonExistsPillIndexException extends NotExistsException {
    public NonExistsPillIndexException(){
        super("존재하지 않은 pill 검색", HttpStatus.NOT_FOUND);
    }
}
