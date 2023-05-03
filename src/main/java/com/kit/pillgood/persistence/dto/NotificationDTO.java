package com.kit.pillgood.persistence.dto;

import lombok.*;

import com.kit.pillgood.domain.User;

import java.time.LocalDateTime;

@Data
@Builder
public class NotificationDTO {
    private Long notificationIndex;
    private User user;
    private String notificationContent;
    private LocalDateTime notificationTime;
    private boolean notificationCheck;
}
