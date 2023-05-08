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
    private Long takePillIndex;

    private Prescription prescription;

    private Pill pill;
    @Builder.Default
    private List<TakePillCheck> takePillCheck = new ArrayList<>();

    @NotEmpty(message = "takeDay은 필수 값 입니다.")
    private Integer takeDay;

    @NotEmpty(message = "takeCount은 필수 값 입니다.")
    private Integer takeCount;
}
