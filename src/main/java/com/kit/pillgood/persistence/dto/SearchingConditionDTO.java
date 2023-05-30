package com.kit.pillgood.persistence.dto;

import lombok.*;

import javax.validation.constraints.NotEmpty;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SearchingConditionDTO {

    @NotEmpty(groups = {ValidationGroups.groupSearch.class}, message = "약 이름은 필수 값입니다.")
    private String pillName;
    private String pillShape;
    private String pillColor;
    private String pillFrontWord;
    private String pillBackWord;
}
