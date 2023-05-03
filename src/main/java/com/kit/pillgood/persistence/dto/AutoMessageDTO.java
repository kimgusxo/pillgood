package com.kit.pillgood.persistence.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
public class AutoMessageDTO {
    private String diseaseName;
    private LocalDateTime takeDate;
    private LocalDateTime takePillTime;
    private String messageContent;
}
