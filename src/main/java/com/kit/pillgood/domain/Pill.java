package com.kit.pillgood.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Pill {

    @Id
    @Column(name = "PILL_INDEX")
    private String pillIndex;

    @Column(name = "PILL_NUM")
    private String pillNum;

    @Column(name = "PILL_FRONT_WORD")
    private String pillFrontWord;

    @Column(name = "PILL_BACK_WORD")
    private String pillBackWord;

    @Column(name = "PILL_SHAPE")
    private String pillShape;

    @Column(name = "PILL_COLOR")
    private String pillColor;

    @Column(name = "PILL_CATEGORY_NAME")
    private String pillCategoryName;

    @Column(name = "PILL_FORMULATION")
    private String pillFormulation;

    @Column(name = "PILL_EFFECT")
    private String pillEffect;

    @Column(name = "PILL_PRECAUTION")
    private String pillPrecaution;

}
