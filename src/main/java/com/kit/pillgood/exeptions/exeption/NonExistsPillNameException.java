package com.kit.pillgood.exeptions.exeption;

import com.kit.pillgood.exeptions.exeption.superExeption.NotExistsException;
import org.springframework.http.HttpStatus;

public class NonExistsPillNameException extends NotExistsException {
    public NonExistsPillNameException(){
        super("존재하지 않은 pillNmae 검색", HttpStatus.NOT_FOUND);
    }
}
