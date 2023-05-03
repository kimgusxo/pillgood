package com.kit.pillgood.persistence.dto;

import com.kit.pillgood.domain.TakePill;
import lombok.*;

import java.time.LocalDate;

@Data
@Builder
public class TakePillCheckDTO {
    private Long takePillCheckIndex;
    private TakePill takePill;
    private LocalDate takeDate;
    private Integer takePillTime;
    private Boolean takeCheck;
}
