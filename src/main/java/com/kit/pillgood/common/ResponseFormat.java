package com.kit.pillgood.common;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ResponseFormat {
    private int statusCode;
    private String message;
    private Object data;

    public static ResponseFormat of(String message, int statusCode, Object data){
        return ResponseFormat.builder()
                .message(message)
                .statusCode(statusCode)
                .data(data)
                .build();
    }

    public static ResponseFormat of(String message, int statusCode){
        return ResponseFormat.builder()
                .message(message)
                .statusCode(statusCode)
                .build();
    }
}
