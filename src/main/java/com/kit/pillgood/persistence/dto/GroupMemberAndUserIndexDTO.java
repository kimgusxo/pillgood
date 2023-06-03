package com.kit.pillgood.persistence.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GroupMemberAndUserIndexDTO {
    private Long groupMemberIndex;

    @NotNull(groups = {ValidationGroups.groupCreate.class}, message = "userIndex는 필수 값입니다.")
    private Long userIndex;

    @NotEmpty(groups = {ValidationGroups.groupCreate.class}, message = "그룹원 이름은 필수 값입니다.")
    private String groupMemberName;

    @NotNull(groups = {ValidationGroups.groupCreate.class}, message = "생일은 필수 값입니다.")
    @JsonFormat(pattern = "YYYY-MM-DD")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate groupMemberBirth;

    @NotEmpty(groups = {ValidationGroups.groupCreate.class}, message = "핸드폰 번호는 필수 값입니다.")
    private String groupMemberPhone;

    @NotNull(groups = {ValidationGroups.groupCreate.class}, message = "messageCheck는 필수 값입니다.")
    private Boolean messageCheck;
}
