package com.kit.pillgood.persistence.projections;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class PrescriptionIndexAndDiseaseIndexSummary {
    private Long prescriptionIndex;
    private Long diseaseIndex;
}
