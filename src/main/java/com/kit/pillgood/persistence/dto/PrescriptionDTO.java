package com.kit.pillgood.persistence.dto;

import com.kit.pillgood.domain.Disease;
import com.kit.pillgood.domain.GroupMember;
import com.kit.pillgood.domain.TakePill;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PrescriptionDTO {

    @NotNull(message = "prescriptionIndex 누락")
    private Long prescriptionIndex;

    @NotNull(message = "groupMember 누락")
    private Long groupMemberIndex;

    @NotNull(message = "disease 누락")
    private Long diseaseIndex;


    @NotEmpty(message = "prescriptionRegistrationDate 누락")
    private LocalDate prescriptionRegistrationDate;

    @NotEmpty(message = "prescriptionDate 누락")
    private LocalDate prescriptionDate;

    private String hospitalPhone;

    @NotEmpty(message = "hospitalName 누락")
    private String hospitalName;
}
