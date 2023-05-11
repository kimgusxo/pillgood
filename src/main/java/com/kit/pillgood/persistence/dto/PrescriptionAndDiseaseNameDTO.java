package com.kit.pillgood.persistence.dto;

import com.kit.pillgood.domain.Disease;
import com.kit.pillgood.domain.GroupMember;
import com.kit.pillgood.domain.TakePill;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class PrescriptionAndDiseaseNameDTO {
    private Long prescriptionIndex;
    private GroupMember groupMember;
    private Disease disease;
    @Builder.Default
    private List<TakePill> takePills = new ArrayList<>();
    private LocalDate prescriptionRegistrationDate;
    private LocalDate prescriptionDate;
    private String hospitalPhone;
    private String hospitalName;
}
