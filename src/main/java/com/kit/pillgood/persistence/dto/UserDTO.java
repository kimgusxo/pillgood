package com.kit.pillgood.persistence.dto;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private Long userIndex;

    @NotEmpty(groups = {ValidationGroups.groupUpdate.class, ValidationGroups.groupDelete.class},message = "userEmail은 필수 값입니다.")
    @Email(groups = {ValidationGroups.groupUpdate.class, ValidationGroups.groupDelete.class})
    private String userEmail;

    @NotEmpty(groups = {ValidationGroups.groupUpdate.class, ValidationGroups.groupDelete.class}, message = "userFcmToken은 필수 값입니다.")
    private String userFcmToken;

    
}
