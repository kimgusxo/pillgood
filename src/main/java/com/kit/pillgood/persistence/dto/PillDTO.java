package com.kit.pillgood.persistence.dto;

import lombok.*;

import javax.validation.constraints.NotEmpty;

@Data
@Builder
public class PillDTO {

    @NotEmpty(message = "pillIndex 누락")
    private Long pillIndex;

    @NotEmpty(message = "pillNum 누락")
    private String pillNum;

    @NotEmpty(message = "pillName 누락")
    private String pillName;

    private String pillFrontWord;

    private String pillBackWord;

    private String pillShape;

    private String pillColor;

    private String pillCategoryName;

    private String pillFormulation;

    private String pillEffect;

    private String pillPrecaution;

}
