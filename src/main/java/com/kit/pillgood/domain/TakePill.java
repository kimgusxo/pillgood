package com.kit.pillgood.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class  TakePill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TAKE_PILL_INDEX")
    private Long takePillIndex;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="PRESCRIPTION_INDEX", nullable = false)
    @JsonIgnore
    @NotNull
    private Prescription prescription;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="PILL_INDEX", nullable = false)
    @JsonIgnore
    @NotNull
    private Pill pill;

    @Builder.Default
    @OneToMany(mappedBy = "takePill", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<TakePillCheck> takePillCheck = new ArrayList<>();

    @Column(name = "TAKE_DAY", nullable = false)
    @NotNull
    private Integer takeDay;

    @Column(name = "TAKE_COUNT", nullable = false)
    @NotNull
    private Integer takeCount;
}
