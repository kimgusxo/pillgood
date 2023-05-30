package com.kit.pillgood.persistence.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotificationContentDTO {
    private Long userIndex;
    private String groupMemberName;
    private int takePillTime;
    private String userFcmToken;
}
