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
@NoArgsConstructor
@AllArgsConstructor
public class TakePillDTO {
    private Long takePillIndex;

    private Long prescriptionIndex;

    private Long pillIndex;

    private Integer takeDay;

    private Integer takeCount;
}
