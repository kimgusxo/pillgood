package com.kit.pillgood.persistence.dto;

import com.kit.pillgood.domain.Prescription;
import com.kit.pillgood.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GroupMemberAndUserIndexDTO {
    private Long groupMemberIndex;
    private Long userIndex;
    private String groupMemberName;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate groupMemberBirth;
    private String groupMemberPhone;
    private Boolean messageCheck;
}
