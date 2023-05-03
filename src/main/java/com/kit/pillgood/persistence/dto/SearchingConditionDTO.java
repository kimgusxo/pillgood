package com.kit.pillgood.persistence.dto;

import lombok.*;

@Data
@Builder
public class SearchingConditionDTO {
    private String pillName;
    private String pillShape;
    private String pillColor;
    private String pillFrontWord;
    private String pillBackWord;
}
