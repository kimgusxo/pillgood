package com.kit.pillgood.persistence.dto;

import lombok.*;

import javax.validation.constraints.NotEmpty;

@Data
@Builder
public class DiseaseDTO {
    private Long diseaseIndex;
    private String diseaseClassification;
    private String diseaseCode;
    private String diseaseName;
}
