package com.kit.pillgood.persistence.dto;

import com.kit.pillgood.domain.GroupMember;
import com.kit.pillgood.domain.Notification;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class UserDTO {
    private Long userIndex;
    private String userEmail;
    private String userFcmToken;
    private List<GroupMember> groupMembers = new ArrayList<>();
    private List<Notification> notifications = new ArrayList<>();
}
