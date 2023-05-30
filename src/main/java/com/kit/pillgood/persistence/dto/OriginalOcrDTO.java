package com.kit.pillgood.persistence.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OriginalOcrDTO {
    String hospitalName;
    String phoneNumber;
    String diseaseCode;
    List<PillScheduleDTO> pillNameList = new ArrayList<>();
}
