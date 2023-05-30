package com.kit.pillgood.persistence.dto;

import lombok.*;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TakePillCheckAndGroupMemberIndexDTO {
    private Long userIndex;
    private LocalDate takeDateStart;
    private LocalDate takeDateEnd;
    private LocalDate dateStart;
    private LocalDate dateEnd;

}
