package com.kit.pillgood.persistence.dto;

import com.kit.pillgood.domain.GroupMember;
import com.kit.pillgood.domain.Notification;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class UserDTO {
    @NotEmpty(message = "userIndex 누락")
    private Long userIndex;

    @NotEmpty(message = "userEmail 누락")
    @Email
    private String userEmail;

    @NotEmpty(message = "userFcmToken 누락")
    private String userFcmToken;

    @Builder.Default
    @NotEmpty(message = "groupMembers 누락")
    private List<GroupMember> groupMembers = new ArrayList<>();

    @Builder.Default
    @NotEmpty(message = "notifications 누락")
    private List<Notification> notifications = new ArrayList<>();
    

}
