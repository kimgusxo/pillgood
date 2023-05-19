package com.kit.pillgood.persistence.dto;

import lombok.*;

import javax.validation.constraints.NotEmpty;

@Data
@Builder
public class DiseaseDTO {

    @NotEmpty(groups = {ValidationGroups.groupUpdate.class})
    private Long diseaseIndex;

    private String diseaseClassification;

    @NotEmpty(groups = {ValidationGroups.groupCreate.class})
    private String diseaseCode;

    @NotEmpty(groups = {ValidationGroups.groupCreate.class})
    private String diseaseName;
}
