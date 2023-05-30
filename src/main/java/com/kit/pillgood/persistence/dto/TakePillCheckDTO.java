package com.kit.pillgood.persistence.dto;

import com.kit.pillgood.domain.TakePill;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
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
