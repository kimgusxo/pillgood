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

    private Long userIndex;

    private String groupMemberName;

    private LocalDate groupMemberBirth;

    private String groupMemberPhone;

    private Boolean messageCheck;
}
