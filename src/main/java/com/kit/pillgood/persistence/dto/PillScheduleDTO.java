package com.kit.pillgood.persistence.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PillScheduleDTO {
    String pillName;
    Integer takeDay;
    Integer takeCount;

}
