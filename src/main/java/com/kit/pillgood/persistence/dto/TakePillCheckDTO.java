package com.kit.pillgood.persistence.dto;

import com.kit.pillgood.domain.TakePill;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;

@Data
@Builder
public class TakePillCheckDTO {
    @NotEmpty(message = "takePillCheckIndex 누락")
    private Long takePillCheckIndex;

    @NotEmpty(message = "takePill 누락")
    private TakePill takePill;

    @NotEmpty(message = "takeDate 누락")
    private LocalDate takeDate;

    @NotEmpty(message = "takePillTime 누락")
    private Integer takePillTime;

    @NotEmpty(message = "takeCheck 누락")
    @Pattern(regexp = "[01]", message = "boolean 값으로 입력하세요")
    private Boolean takeCheck;
}
