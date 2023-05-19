package com.kit.pillgood.exeptions;

import com.kit.pillgood.exeptions.exeption.AlreadyExistUserException;
import com.kit.pillgood.exeptions.exeption.NonRegistrationFirebaseException;
import com.kit.pillgood.exeptions.exeption.NonRegistrationGroupException;
import com.kit.pillgood.exeptions.exeption.NonRegistrationUserException;
import com.kit.pillgood.exeptions.exeption.superExeption.AlreadyExistException;
import com.kit.pillgood.exeptions.exeption.superExeption.EtcFirebaseException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.validation.BindException;

import java.net.ConnectException;
import java.sql.SQLException;
import java.util.Objects;


@RestControllerAdvice
public class CommonBindingException {
    @ExceptionHandler(NonRegistrationFirebaseException.class)
    @ResponseBody
    public ErrorResponse handleNonRegistrationFirebaseExeption(NonRegistrationFirebaseException e){
        return ErrorResponse.of(e.getMessage(), e.getHttpStatus().value());
    }

    @ExceptionHandler(NonRegistrationUserException.class)
    @ResponseBody
    public ErrorResponse handleNonRegistrationUserExeption(NonRegistrationUserException e){
        return ErrorResponse.of(e.getMessage(), e.getHttpStatus().value());
    }

    @ExceptionHandler(NonRegistrationGroupException.class)
    @ResponseBody
    public ErrorResponse handleNonRegistrationGroupExeption(NonRegistrationGroupException e){
        return ErrorResponse.of(e.getMessage(), e.getHttpStatus().value());
    }

    @ExceptionHandler(EtcFirebaseException.class)
    @ResponseBody
    public ErrorResponse handleEtcFirebaseException(EtcFirebaseException e){
        return ErrorResponse.of(e.getMessage(), e.getHttpStatus().value());
    }

    @ExceptionHandler(AlreadyExistUserException.class)
    @ResponseBody
    public ErrorResponse hadleAlreadyExistException(AlreadyExistUserException e){
        return ErrorResponse.of(e.getMessage(), e.getHttpStatus().value());
    }

    @ExceptionHandler(BindException.class)
    @ResponseBody
    public ErrorResponse handleBindException(BindException e){
        return ErrorResponse.of(Objects.requireNonNull(e.getFieldError()).getDefaultMessage(), HttpStatus.BAD_REQUEST.value());
    }

    @ExceptionHandler(SQLException.class)
    @ResponseBody
    public ErrorResponse handleSQLException(SQLException e){
        return ErrorResponse.of("SQL Exception", e.getErrorCode());
    }

}
