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
public class PillScheduleDTO {
    String pillName;
    Integer takeCount;
    Integer takeDay;
    List<Integer> takePillTimeList = new ArrayList<>();

}
