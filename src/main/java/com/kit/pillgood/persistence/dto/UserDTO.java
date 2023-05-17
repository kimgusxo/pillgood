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
    private Long userIndex;

    @NotEmpty(groups = {ValidationGroups.groupUpdate.class},message = "userEmail은 필수 값입니다.")
    @Email(groups = {ValidationGroups.groupUpdate.class})
    private String userEmail;

    @NotEmpty(groups = {ValidationGroups.groupUpdate.class}, message = "userFcmToken은 필수 값입니다.")
    private String userFcmToken;

    
}
