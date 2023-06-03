package com.kit.pillgood.persistence.dto;

import lombok.*;

import javax.validation.constraints.NotEmpty;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SearchingConditionDTO {
    private String pillName;
    private String pillShape;
    private String pillColor;
    private String pillFrontWord;
    private String pillBackWord;
}
