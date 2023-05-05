package com.kit.pillgood.persistence.dto;

import com.kit.pillgood.domain.Pill;
import com.kit.pillgood.domain.Prescription;
import com.kit.pillgood.domain.TakePillCheck;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class TakePillDTO {
    private Long takePillIndex;
    private Prescription prescription;
    private Pill pill;
    @Builder.Default
    private List<TakePillCheck> takePillCheck = new ArrayList<>();
    private Integer takeDay;
    private Integer takeCount;
}
