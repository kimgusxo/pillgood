package com.kit.pillgood.exeptions.exeption;

import com.kit.pillgood.exeptions.exeption.superExeption.NotFoundException;
import org.springframework.http.HttpStatus;

public class NonFoundPillNameException extends NotFoundException {
    public NonFoundPillNameException(){
        super("존재하지 않은 pillNmae 검색", HttpStatus.NOT_FOUND);
    }
}
