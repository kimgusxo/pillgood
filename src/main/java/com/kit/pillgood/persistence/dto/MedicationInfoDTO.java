package com.kit.pillgood.persistence.dto;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MedicationInfoDTO {
    private Long groupMemberIndex;
    private String groupMemberName;
    private Long pillIndex;
    private String pillNum;
    private String pillName;
    private String pillFrontWord;
    private String pillBackWord;
    private String pillShape;
    private String pillColor;
    private String pillCategoryName;
    private String pillFormulation;
    private String pillEffect;
    private String pillPrecaution;
    private Long diseaseIndex;
    private String diseaseName;
    private Long takePillCheckIndex;
    private Boolean takeCheck;
    private Integer takePillTime;
}
