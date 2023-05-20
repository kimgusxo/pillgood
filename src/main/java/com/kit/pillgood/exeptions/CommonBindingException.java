package com.kit.pillgood.exeptions;

import com.kit.pillgood.exeptions.exeption.*;
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
    public ErrorResponse hadleAlreadyExistUserException(AlreadyExistUserException e){
        return ErrorResponse.of(e.getMessage(), e.getHttpStatus().value());
    }

    @ExceptionHandler(AlreadyExistGroupException.class)
    @ResponseBody
    public ErrorResponse hadleAlreadyExistGroupException(AlreadyExistGroupException e){
        return ErrorResponse.of(e.getMessage(), e.getHttpStatus().value());
    }

    @ExceptionHandler(NonFoundPillIndexException.class)
    @ResponseBody
    public ErrorResponse handleNonFoundPillIndexException(NonFoundPillIndexException e){
        return ErrorResponse.of(e.getMessage(), e.getHttpStatus().value());
    }

    @ExceptionHandler(NonFoundPillNameException.class)
    @ResponseBody
    public ErrorResponse handleNonFoundPillNameException(NonFoundPillNameException e){
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
