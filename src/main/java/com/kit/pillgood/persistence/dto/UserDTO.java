package com.kit.pillgood.persistence.dto;

import com.kit.pillgood.domain.GroupMember;
import com.kit.pillgood.domain.Notification;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class UserDTO {
    private Long userIndex;

    @NotEmpty(message = "userEmail은 필수 값 입니다.")
    @Pattern(regexp = "[a-z0-9]+@[a-z]+\\.[a-z]{2,3}", message = "메시지 형식으로 입력하세요")
    private String userEmail;

    @NotEmpty(message = "userFcmToken은 필수 값 입니다.")
    private String userFcmToken;

    @Builder.Default
    private List<GroupMember> groupMembers = new ArrayList<>();

    @Builder.Default
    private List<Notification> notifications = new ArrayList<>();
    

}
