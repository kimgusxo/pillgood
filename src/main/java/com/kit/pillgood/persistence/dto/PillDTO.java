package com.kit.pillgood.persistence.dto;

import lombok.*;

@Data
@Builder
public class PillDTO {
    private String pillIndex;
    private String pillNum;
    private String pillFrontWord;
    private String pillBackWord;
    private String pillShape;
    private String pillColor;
    private String pillCategoryName;
    private String pillFormulation;
    private String pillEffect;
    private String pillPrecaution;

}
