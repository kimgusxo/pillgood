package com.kit.pillgood.persistence.dto;

import com.kit.pillgood.domain.Prescription;
import com.kit.pillgood.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GroupMemberAndUserIndexDTO {
    @NotEmpty(groups = {ValidationGroups.groupUpdate.class}, message = "groupIndex는 필수 값입니다.")
    private Long groupMemberIndex;

    @NotEmpty(groups = {ValidationGroups.groupCreate.class}, message = "userIndex는 필수 값입니다.")
    private Long userIndex;

    @NotEmpty(groups = {ValidationGroups.groupCreate.class}, message = "그룹원 이름은 필수 값입니다.")
    private String groupMemberName;

    @NotEmpty(groups = {ValidationGroups.groupCreate.class}, message = "생일은 필수 값입니다.")
    @Future(groups = {ValidationGroups.groupCreate.class}, message = "LocalDate type 형식으로 입력하세요(yyyy-MM-dd)")
    private LocalDate groupMemberBirth;

    @NotEmpty(groups = {ValidationGroups.groupCreate.class}, message = "핸드폰 번호는 필수 값입니다.")
    private String groupMemberPhone;

    @NotEmpty(groups = {ValidationGroups.groupCreate.class}, message = "messageCheck는 필수 값입니다.")
    @Pattern(regexp = "[01]", groups = {ValidationGroups.groupCreate.class, ValidationGroups.groupUpdate.class}, message = "true 1 또는 false 0 중에 입력하세요")
    private Boolean messageCheck;
}
