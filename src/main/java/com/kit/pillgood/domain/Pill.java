package com.kit.pillgood.domain;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class Pill {

    @Id
    @Column(name = "PILL_INDEX")
    private String pillIndex;

    @Column(name = "PILL_NUM", nullable = false)
    @NotNull
    private String pillNum;

    @Column(name = "PILL_NAME", nullable = false)
    @NotNull
    private String pillName;

    @Column(name = "PILL_FRONT_WORD", nullable = false)
    private String pillFrontWord;

    @Column(name = "PILL_BACK_WORD", nullable = false)
    private String pillBackWord;

    @Column(name = "PILL_SHAPE", nullable = false)
    private String pillShape;

    @Column(name = "PILL_COLOR", nullable = false)
    private String pillColor;

    @Column(name = "PILL_CATEGORY_NAME", nullable = false)
    private String pillCategoryName;

    @Column(name = "PILL_FORMULATION", nullable = false)
    private String pillFormulation;

    @Column(name = "PILL_EFFECT", nullable = false)
    private String pillEffect;

    @Column(name = "PILL_PRECAUTION", nullable = false)
    private String pillPrecaution;

}
