package com.kit.pillgood.persistence.dto;

import lombok.*;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TakePillCheckDTO {
    private Long takePillCheckIndex;

    private Long takePillIndex;

    private LocalDate takeDate;

    private Integer takePillTime;

    private Boolean takeCheck;
}
