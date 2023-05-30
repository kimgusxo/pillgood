package com.kit.pillgood.persistence.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class EditOcrDTO {
    Long groupMemberIndex;
    String groupMemberName;
    LocalDate startDate;
    String hospitalName;
    String phoneNumber;
    String diseaseCode;
    List<PillScheduleDTO> pillList = new ArrayList<>();

}
