package com.kit.pillgood.persistence.dto;

import lombok.*;

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
