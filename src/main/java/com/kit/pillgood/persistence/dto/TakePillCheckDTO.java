package com.kit.pillgood.persistence.dto;

import com.kit.pillgood.domain.TakePill;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;

@Data
@Builder
public class TakePillCheckDTO {
    private Long takePillCheckIndex;

    private TakePill takePill;

    @NotEmpty(message = "takeDate은 필수 값 입니다.")
    private LocalDate takeDate;

    @NotEmpty(message = "takePillTime은 필수 값 입니다.")
    private Integer takePillTime;

    @NotEmpty(message = "takeCheck은 필수 값 입니다.")
    @Pattern(regexp = "[01]", message = "0 또는 1 값만 허용")
    private Boolean takeCheck;
}
