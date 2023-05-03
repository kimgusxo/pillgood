package com.kit.pillgood.persistence.dto;

import lombok.*;

@Data
@Builder
public class DiseaseDTO {
    private Long diseaseIndex;
    private String diseaseClassification;
    private String diseaseCode;
    private String diseaseName;
}
