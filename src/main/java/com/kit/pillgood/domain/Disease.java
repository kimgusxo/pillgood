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
public class Disease {
    @Id
    @Column(name = "DISEASE_INDEX")
    private Long diseaseIndex;

    @Column(name = "DISEASE_CLASSFICATION")
    private String diseaseClassification;

    @Column(name = "DISEASE_CODE", nullable = false)
    @NotNull
    private String diseaseCode;

    @Column(name = "DISEASE_NAME", nullable = false)
    @NotNull
    private String diseaseName;
}
