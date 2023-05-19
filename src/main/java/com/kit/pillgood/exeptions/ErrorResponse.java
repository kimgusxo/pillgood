package com.kit.pillgood.exeptions;

import lombok.Builder;
import lombok.Getter;

// 예외처리 리턴 형식 지정
@Getter
@Builder
public class ErrorResponse {
    private String message;
    private int status;

    public static ErrorResponse of(String message, int status){
        return ErrorResponse.builder()
                .message(message)
                .status(status)
                .build();
    }
}
