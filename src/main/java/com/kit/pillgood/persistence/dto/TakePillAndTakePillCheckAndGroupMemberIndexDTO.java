package com.kit.pillgood.persistence.dto;

import com.kit.pillgood.persistence.projection.TakePillAndTakePillCheckSummary;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class TakePillAndTakePillCheckAndGroupMemberIndexDTO {
    Long groupMemberIndex;
    List<TakePillAndTakePillCheckSummary> takePillAndTakePillCheckSummaries;

}
