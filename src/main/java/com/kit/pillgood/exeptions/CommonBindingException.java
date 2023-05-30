package com.kit.pillgood.exeptions;

import com.kit.pillgood.common.ResponseFormat;
import com.kit.pillgood.exeptions.exeption.*;
import com.kit.pillgood.exeptions.exeption.superExeption.EtcFirebaseException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.validation.BindException;

import java.sql.SQLException;
import java.util.Objects;


@RestControllerAdvice
public class CommonBindingException {
    @ExceptionHandler(NonRegistrationFirebaseException.class)
    @ResponseBody
    public ResponseFormat handleNonRegistrationFirebaseExeption(NonRegistrationFirebaseException e){
        return ResponseFormat.of(e.getMessage(), e.getHttpStatus().value());
    }

    @ExceptionHandler(NonRegistrationUserException.class)
    @ResponseBody
    public ResponseFormat handleNonRegistrationUserExeption(NonRegistrationUserException e){
        return ResponseFormat.of(e.getMessage(), e.getHttpStatus().value());
    }

    @ExceptionHandler(NonRegistrationGroupException.class)
    @ResponseBody
    public ResponseFormat handleNonRegistrationGroupExeption(NonRegistrationGroupException e){
        return ResponseFormat.of(e.getMessage(), e.getHttpStatus().value());
    }

    @ExceptionHandler(EtcFirebaseException.class)
    @ResponseBody
    public ResponseFormat handleEtcFirebaseException(EtcFirebaseException e){
        return ResponseFormat.of(e.getMessage(), e.getHttpStatus().value());
    }

    @ExceptionHandler(AlreadyExistUserException.class)
    @ResponseBody
    public ResponseFormat hadleAlreadyExistUserException(AlreadyExistUserException e){
        return ResponseFormat.of(e.getMessage(), e.getHttpStatus().value());
    }

    @ExceptionHandler(AlreadyExistGroupException.class)
    @ResponseBody
    public ResponseFormat hadleAlreadyExistGroupException(AlreadyExistGroupException e){
        return ResponseFormat.of(e.getMessage(), e.getHttpStatus().value());
    }

    @ExceptionHandler(NonExistsPillIndexException.class)
    @ResponseBody
    public ResponseFormat handleNonExistsPillIndexException(NonExistsPillIndexException e){
        return ResponseFormat.of(e.getMessage(), e.getHttpStatus().value());
    }

    @ExceptionHandler(NonExistsPillNameException.class)
    @ResponseBody
    public ResponseFormat handleNonExistsPillNameException(NonExistsPillNameException e){
        return ResponseFormat.of(e.getMessage(), e.getHttpStatus().value());
    }

    @ExceptionHandler(NonExistsPrescriptionIndexException.class)
    @ResponseBody
    public ResponseFormat handleNonExistsPrescriptionIndexException(NonExistsPrescriptionIndexException e){
        return ResponseFormat.of(e.getMessage(), e.getHttpStatus().value());
    }
    @ExceptionHandler(NonExistsMedicationInfoException.class)
    @ResponseBody
    public ResponseFormat handleNonExistsMedicationInfoException(NonExistsMedicationInfoException e){
        return ResponseFormat.of(e.getMessage(), e.getHttpStatus().value());
    }

    @ExceptionHandler(BindException.class)
    @ResponseBody
    public ResponseFormat handleBindException(BindException e){
        return ResponseFormat.of(Objects.requireNonNull(e.getFieldError()).getDefaultMessage(), HttpStatus.BAD_REQUEST.value());
    }


    @ExceptionHandler(SQLException.class)
    @ResponseBody
    public ResponseFormat handleSQLException(SQLException e){
        return ResponseFormat.of("SQL Exception", e.getErrorCode());
    }

}
