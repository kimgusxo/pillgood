package com.kit.pillgood.exeptions.exeption;

import com.kit.pillgood.exeptions.exeption.superExeption.AlreadyExistException;
import org.springframework.http.HttpStatus;

public class AlreadyExistGroupException extends AlreadyExistException {
    public AlreadyExistGroupException() {
        super("이미 등록된 그룹원 입니다.", HttpStatus.BAD_REQUEST);
    }

}
