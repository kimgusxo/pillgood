package com.kit.pillgood.exeptions.exeption;

import com.kit.pillgood.exeptions.exeption.superExeption.NotExistsException;
import org.springframework.http.HttpStatus;

public class NonExistsMedicationInfoException extends NotExistsException {
    public NonExistsMedicationInfoException(){
        super("약 정보가 존재하지 않습니다.", HttpStatus.NOT_FOUND);
    }

}
