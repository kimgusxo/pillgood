package com.kit.pillgood.persistence.dto;

import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class OriginalOcrDTO {
    String hospitalName;
    String phoneNumber;
    String diseaseCode;
    List<PillScheduleDTO> pillNameList = new ArrayList<>();
}
