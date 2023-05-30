package com.kit.pillgood.persistence.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kit.pillgood.persistence.projection.TakePillAndTakePillCheckSummary;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TakePillAndTakePillCheckAndGroupMemberIndexDTO {
    Long groupMemberIndex;
    List<TakePillAndTakePillCheckDTO> takePillAndTakePillCheckDTOs;

}
