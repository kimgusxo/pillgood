package com.kit.pillgood.persistence.dto;

import lombok.*;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
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
