package com.kit.pillgood.persistence.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class TakePillAndTakePillCheckAndGroupMemberIndexDTO {
    Long groupMemberIndex;
    List<TakePillAndTakePillCheckDTO> takePillAndTakePillCheckDTOList;
}
