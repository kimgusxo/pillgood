package com.kit.pillgood.persistence.dto;

import com.kit.pillgood.domain.Pill;
import com.kit.pillgood.domain.Prescription;
import com.kit.pillgood.domain.TakePill;
import com.kit.pillgood.domain.TakePillCheck;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
