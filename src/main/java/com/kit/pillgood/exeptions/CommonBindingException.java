package com.kit.pillgood.exeptions;

import com.kit.pillgood.exeptions.exeption.NonRegistrationFirebaseException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;


@ControllerAdvice
public class CommonBindingException {
    @ExceptionHandler(NonRegistrationFirebaseException.class)
    @ResponseBody
    public ErrorResponse handleNonRegistrationFirebaseExeption(Exception e){
        return ErrorResponse.of(e.getMessage(), HttpStatus.UNAUTHORIZED.value());
    }

}
