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
    private Long prescriptionIndex;

    private GroupMember groupMember;

    private Disease disease;

    @Builder.Default
    private List<TakePill> takePills = new ArrayList<>();

    @NotEmpty(message = "prescriptionRegistrationDate은 필수 값 입니다.")
    private LocalDate prescriptionRegistrationDate;

    @NotEmpty(message = "prescriptionDate은 필수 값 입니다.")
    private LocalDate prescriptionDate;

    private String hospitalPhone;

    @NotEmpty(message = "hospitalName은 필수 값 입니다.")
    private String hospitalName;
}
