package com.kit.pillgood.persistence.dto;

import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class PillScheduleDTO {
    String pillName;
    Integer takeDay;
    Integer takeCount;
    List<Integer> takePillTimeList = new ArrayList<>();

}
