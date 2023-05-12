package com.kit.pillgood.persistence.dto;

import com.kit.pillgood.domain.Pill;
import com.kit.pillgood.domain.Prescription;
import com.kit.pillgood.domain.TakePillCheck;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class TakePillDTO {
    @NotEmpty(message = "takePillIndex 누락")
    private Long takePillIndex;

    @NotEmpty(message = "prescription 누락")
    private Prescription prescription;

    @NotEmpty(message = "pill 누락")
    private Pill pill;

    private List<Long> takePillCheckIndexList = new ArrayList<>();

    @NotEmpty(message = "takeDay 누락")
    private Integer takeDay;

    @NotEmpty(message = "takeCount 누락")
    private Integer takeCount;
}
