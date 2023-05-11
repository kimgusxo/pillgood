package com.kit.pillgood.persistence.dto;

import lombok.*;

import javax.validation.constraints.NotEmpty;

@Data
@Builder
public class DiseaseDTO {

    @NotEmpty
    private Long diseaseIndex;

    private String diseaseClassification;

    @NotEmpty
    private String diseaseCode;

    @NotEmpty
    private String diseaseName;
}
