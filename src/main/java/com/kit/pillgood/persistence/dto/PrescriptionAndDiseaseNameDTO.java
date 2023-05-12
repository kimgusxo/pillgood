package com.kit.pillgood.persistence.dto;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDate;

@Data
@Builder
public class PrescriptionAndDiseaseNameDTO {
    private Long prescriptionIndex;
    private Long groupMemberIndex;
    private Long diseaseIndex;
    private LocalDate prescriptionRegistrationDate;
    private LocalDate prescriptionDate;
    private String hospitalPhone;
    private String hospitalName;
    private String diseaseName;
}
