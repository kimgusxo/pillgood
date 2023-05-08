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
    private Long groupMemberIndex;

    private User user;

    @Builder.Default
    private List<Prescription> prescriptions = new ArrayList<>();

    @NotEmpty(message = "takeCheck은 필수 값 입니다.")
    private String groupMemberName;

    private LocalDate groupMemberBirth;

    @Pattern(regexp = "/^\\d{3}-\\d{3,4}-\\d{4}$/;", message = "0 또는 1 값만 허용")
    private String groupMemberPhone;

    @NotEmpty(message = "takeCheck은 필수 값 입니다.")
    @Pattern(regexp = "[01]", message = "0 또는 1 값만 허용")
    private Boolean messageCheck;
}
