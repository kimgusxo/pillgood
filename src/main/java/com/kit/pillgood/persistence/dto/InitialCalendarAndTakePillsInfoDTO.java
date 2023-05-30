package com.kit.pillgood.persistence.dto;

import lombok.*;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InitialCalendarAndTakePillsInfoDTO {
    private Long userIndex;
    private LocalDate dateStart;
    private LocalDate dateCur;
    private LocalDate dateEnd;
}
