package com.kit.pillgood.persistence.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TakePillAndTakePillCheckDTO {
    private Long takePillIndex;

    private Long prescriptionIndex;

    private Long pillIndex;

    private Integer takeDay;

    private Integer takeCount;

    private Long takePillCheckIndex;

    private LocalDate takeDate;

    private Integer takePillTime;

    private Boolean takeCheck;
}
