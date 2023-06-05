package com.kit.pillgood.persistence.dto;

import lombok.*;

import com.kit.pillgood.domain.User;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotificationDTO {
    private Long notificationIndex;

    @NotEmpty(message = "userIndex 누락")
    private Long userIndex;

    private String notificationContent;

    @NotEmpty(message = "notificationTime 누락")
    private LocalDateTime notificationTime;

    @NotEmpty(message = "notificationCheck 누락")
    @Pattern(regexp = "[0-1]", message = "boolean 값으로 입력하세요")
    private Boolean notificationCheck;

}
