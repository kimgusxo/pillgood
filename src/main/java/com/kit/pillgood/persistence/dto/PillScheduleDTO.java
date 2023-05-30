package com.kit.pillgood.persistence.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PillScheduleDTO {
    String pillName;
    Integer takeDay;
    Integer takeCount;

}
