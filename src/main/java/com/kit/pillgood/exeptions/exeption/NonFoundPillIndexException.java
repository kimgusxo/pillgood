package com.kit.pillgood.exeptions.exeption;

import com.kit.pillgood.exeptions.exeption.superExeption.NotFoundException;
import org.springframework.http.HttpStatus;

public class NonFoundPillIndexException extends NotFoundException {
    public NonFoundPillIndexException(){
        super("존재하지 않은 pill 검색", HttpStatus.NOT_FOUND);
    }
}
