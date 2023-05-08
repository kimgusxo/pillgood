package com.kit.pillgood.persistence.dto;

import lombok.*;

import com.kit.pillgood.domain.User;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

@Data
@Builder
public class NotificationDTO {
    private Long notificationIndex;
    private User user;
    private String notificationContent;
    @NotEmpty(message = "처방전 시간은 필수 값 입니다.")
    private LocalDateTime notificationTime;
    @NotEmpty(message = "notificationCheck은 필수 값 입니다.")
    private boolean notificationCheck;
}
