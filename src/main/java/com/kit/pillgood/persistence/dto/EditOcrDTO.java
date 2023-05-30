package com.kit.pillgood.persistence.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EditOcrDTO {
    Long groupMemberIndex;
    String groupMemberName;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    LocalDate startDate;
    String hospitalName;
    String phoneNumber;
    String diseaseCode;
    List<PillScheduleDTO> pillList = new ArrayList<>();
}
