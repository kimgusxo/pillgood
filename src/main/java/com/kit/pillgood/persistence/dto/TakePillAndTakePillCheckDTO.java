package com.kit.pillgood.persistence.dto;

import com.kit.pillgood.domain.Pill;
import com.kit.pillgood.domain.Prescription;
import com.kit.pillgood.domain.TakePill;
import com.kit.pillgood.domain.TakePillCheck;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class TakePillAndTakePillCheckDTO {
    @NotEmpty(message = "takePillIndex 누락")
    private Long takePillIndex;

    @NotEmpty(message = "prescription 누락")
    private Long prescriptionIndex;

    @NotEmpty(message = "pill 누락")
    private Long pillIndex;

    @NotEmpty(message = "takeDay 누락")
    private Integer takeDay;

    @NotEmpty(message = "takeCount 누락")
    private Integer takeCount;

    @NotEmpty(message = "takePillCheckIndex 누락")
    private Long takePillCheckIndex;


    @NotEmpty(message = "takeDate 누락")
    private LocalDate takeDate;

    @NotEmpty(message = "takePillTime 누락")
    @Pattern(regexp = "[0-5]", message = "0-5 사이 정수로 입력하세요")
    private Integer takePillTime;

    @NotEmpty(message = "takeCheck 누락")
    @Pattern(regexp = "[01]", message = "boolean 값으로 입력하세요")
    private Boolean takeCheck;
}
