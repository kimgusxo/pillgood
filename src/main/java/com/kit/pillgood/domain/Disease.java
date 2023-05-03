package com.kit.pillgood.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Disease {
    @Id
    @Column(name = "DISEASE_INDEX")
    private Long diseaseIndex;

    @Column(name = "DISEASE_CLASSFICATION")
    private String diseaseClassification;

    @Column(name = "DISEASE_CODE")
    @NonNull
    private String diseaseCode;

    @Column(name = "DISEASE_NAME")
    @NonNull
    private String diseaseName;
}
