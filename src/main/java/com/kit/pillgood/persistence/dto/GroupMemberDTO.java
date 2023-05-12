package com.kit.pillgood.persistence.dto;

import com.kit.pillgood.domain.GroupMember;
import com.kit.pillgood.domain.Prescription;
import com.kit.pillgood.domain.User;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class GroupMemberDTO {

    @NotEmpty(message = "groupMemberIndex 누락")
    private Long groupMemberIndex;

    @NotEmpty(message = "user 누락")
    private Long userIndex;

    @NotEmpty(message = "groupMemberName 누락")
    private String groupMemberName;

    private LocalDate groupMemberBirth;

    private String groupMemberPhone;

    @NotEmpty(message = "messageCheck 누락")
    @Pattern(regexp = "[0-1]", message = "boolean 값으로 입력하세요")
    private Boolean messageCheck;
}
