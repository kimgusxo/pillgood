package com.kit.pillgood.persistence.dto;

import lombok.*;

@Data
@Builder
public class MedicationInfoDTO {
    private Long groupMemberIndex;
    private String groupMemberName;
    private Long pillIndex;
    private String pillName;
    private Long diseaseIndex;
    private String diseaseName;
    private String diseaseProperties;
    private Long takePillCheckIndex;
    private Boolean takeCheck;
    private Integer takePillTime;
}
