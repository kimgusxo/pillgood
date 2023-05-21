package com.kit.pillgood.exeptions.exeption;

import com.kit.pillgood.exeptions.exeption.superExeption.NotExistsException;
import org.springframework.http.HttpStatus;

public class NonExistsPrescriptionIndexException extends NotExistsException {
    public NonExistsPrescriptionIndexException(){
        super("등록되지 않은 처방전 입니다.", HttpStatus.NOT_FOUND);
    }

}
