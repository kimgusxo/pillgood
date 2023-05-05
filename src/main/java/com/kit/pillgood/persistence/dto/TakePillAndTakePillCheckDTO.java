package com.kit.pillgood.persistence.dto;

import com.kit.pillgood.domain.Pill;
import com.kit.pillgood.domain.Prescription;
import com.kit.pillgood.domain.TakePill;
import com.kit.pillgood.domain.TakePillCheck;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class TakePillAndTakePillCheckDTO {
    private Long takePillIndex;
    private Prescription prescription;
    private Pill pill;
    private List<TakePillCheck> takePillCheck = new ArrayList<>();
    private Integer takeDay;
    private Integer takeCount;
    private Long takePillCheckIndex;
    private TakePill takePill;
    private LocalDate takeDate;
    private Integer takePillTime;
    private Boolean takeCheck;
}
