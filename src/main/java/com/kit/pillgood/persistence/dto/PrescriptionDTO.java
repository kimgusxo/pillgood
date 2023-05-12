package com.kit.pillgood.persistence.dto;

import com.kit.pillgood.domain.Disease;
import com.kit.pillgood.domain.GroupMember;
import com.kit.pillgood.domain.TakePill;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class PrescriptionDTO {
    @NotEmpty(message = "prescriptionIndex 누락")
    private Long prescriptionIndex;

    @NotEmpty(message = "groupMember 누락")
    private Long groupMemberIndex;

    @NotEmpty(message = "disease 누락")
    private Disease disease;

    @Builder.Default
    @NotEmpty(message = "takePills 누락")
    private List<Long> takePillIndexList = new ArrayList<>();

    @NotEmpty(message = "prescriptionRegistrationDate 누락")
    private LocalDate prescriptionRegistrationDate;

    @NotEmpty(message = "prescriptionDate 누락")
    private LocalDate prescriptionDate;

    private String hospitalPhone;

    @NotEmpty(message = "hospitalName 누락")
    private String hospitalName;
}
